package com.kosherstore.privateappstore.data.download

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.PowerManager
import com.kosherstore.privateappstore.data.download.engine.StreamDownloadEngine
import com.kosherstore.privateappstore.data.install.InstallCoordinator
import com.kosherstore.privateappstore.data.local.DownloadTaskEntity
import com.kosherstore.privateappstore.data.local.dao.DownloadTaskDao
import com.kosherstore.privateappstore.data.mapper.normalizeChecksumType
import com.kosherstore.privateappstore.di.IoDispatcher
import com.kosherstore.privateappstore.domain.model.DownloadState
import com.kosherstore.privateappstore.domain.model.DownloadStatus
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.util.ChecksumUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Singleton
class DownloadCoordinator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val downloadTaskDao: DownloadTaskDao,
    private val streamDownloadEngine: StreamDownloadEngine,
    private val installCoordinator: InstallCoordinator,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)
    private val runningJobs = ConcurrentHashMap<String, Job>()
    private val _downloadStates = MutableStateFlow<Map<String, DownloadState>>(emptyMap())
    val downloadStates: StateFlow<Map<String, DownloadState>> = _downloadStates.asStateFlow()

    companion object {
        const val MAX_CONCURRENT_DOWNLOADS = 2
    }

    init {
        scope.launch {
            // Observe Room DB for download states
            downloadTaskDao.observeAllTasks().collect { tasks ->
                val stateMap = tasks.associate { task ->
                    val progress = if (task.totalBytes > 0) {
                        ((task.downloadedBytes * 100) / task.totalBytes).toInt()
                    } else {
                        0
                    }
                    task.packageName to DownloadState(
                        progress = progress,
                        status = task.status,
                        localFilePath = task.finalPath.takeIf { task.status == DownloadStatus.COMPLETED },
                        errorMessage = task.errorMessage,
                        autoInstall = task.autoInstall,
                        downloadedBytes = task.downloadedBytes,
                        totalBytes = task.totalBytes,
                        speedBytesPerSec = task.speedBytesPerSec,
                        etaSeconds = task.etaSeconds
                    )
                }
                _downloadStates.value = stateMap
                processQueue()
            }
        }
    }

    suspend fun startDownload(app: StoreApp, autoInstall: Boolean = false) {
        val tempFile = createTempFile(app)
        val finalFile = createFinalFile(app)

        val taskEntity = DownloadTaskEntity(
            packageName = app.packageName,
            appName = app.name,
            iconUrl = app.iconUrl,
            downloadUrl = normalizeDriveUrl(app.apkUrl),
            tempPath = tempFile.absolutePath,
            finalPath = finalFile.absolutePath,
            status = DownloadStatus.PENDING,
            downloadedBytes = if (tempFile.exists()) tempFile.length() else 0L,
            totalBytes = parseSizeToBytes(app.size),
            checksum = app.checksum,
            checksumType = app.checksumType.name,
            autoInstall = autoInstall
        )

        downloadTaskDao.upsertTask(taskEntity)
        startForegroundService()
        processQueue()
    }

    suspend fun resumeDownload(app: StoreApp) {
        val task = downloadTaskDao.getTask(app.packageName)
        if (task != null) {
            downloadTaskDao.updateStatus(app.packageName, DownloadStatus.PENDING)
        } else {
            startDownload(app, false)
        }
        startForegroundService()
        processQueue()
    }

    suspend fun pauseDownload(packageName: String) {
        runningJobs.remove(packageName)?.cancel()
        downloadTaskDao.updateStatus(packageName, DownloadStatus.PAUSED)
    }

    suspend fun cancelDownload(packageName: String) {
        runningJobs.remove(packageName)?.cancel()
        val task = downloadTaskDao.getTask(packageName)
        if (task != null) {
            File(task.tempPath).delete()
            File(task.finalPath).delete()
            downloadTaskDao.deleteTask(packageName)
        }
    }

    suspend fun clear(packageName: String) {
        runningJobs.remove(packageName)?.cancel()
        downloadTaskDao.deleteTask(packageName)
    }

    suspend fun handleSystemDownloadBroadcast(downloadId: Long) {
        // No-op for custom streaming engine
    }

    fun getDownloadedFile(packageName: String): File? {
        val state = _downloadStates.value[packageName] ?: return null
        return state.localFilePath?.let(::File)?.takeIf { it.exists() }
    }

    private fun processQueue() {
        scope.launch {
            val activeCount = runningJobs.size
            if (activeCount >= MAX_CONCURRENT_DOWNLOADS) return@launch

            val activeTasks = downloadTaskDao.getActiveOrPendingTasks()
            val pendingTasks = activeTasks.filter { it.status == DownloadStatus.PENDING }

            for (task in pendingTasks) {
                if (runningJobs.size >= MAX_CONCURRENT_DOWNLOADS) break
                if (!runningJobs.containsKey(task.packageName)) {
                    executeDownloadTask(task)
                }
            }
        }
    }

    private fun executeDownloadTask(task: DownloadTaskEntity) {
        val job = scope.launch {
            downloadTaskDao.updateStatus(task.packageName, DownloadStatus.DOWNLOADING)
            val tempFile = File(task.tempPath)
            val finalFile = File(task.finalPath)

            val callback = object : StreamDownloadEngine.ProgressCallback {
                override fun onProgress(downloadedBytes: Long, totalBytes: Long, speedBytesPerSec: Long, etaSeconds: Long) {
                    val progress = if (totalBytes > 0) ((downloadedBytes * 100) / totalBytes).toInt() else 0
                    val currentMap = _downloadStates.value.toMutableMap()
                    val existing = currentMap[task.packageName] ?: DownloadState()
                    currentMap[task.packageName] = existing.copy(
                        progress = progress,
                        status = DownloadStatus.DOWNLOADING,
                        downloadedBytes = downloadedBytes,
                        totalBytes = totalBytes,
                        speedBytesPerSec = speedBytesPerSec,
                        etaSeconds = etaSeconds
                    )
                    _downloadStates.value = currentMap

                    scope.launch {
                        downloadTaskDao.updateProgress(
                            packageName = task.packageName,
                            downloadedBytes = downloadedBytes,
                            totalBytes = totalBytes,
                            speedBytesPerSec = speedBytesPerSec,
                            etaSeconds = etaSeconds,
                            status = DownloadStatus.DOWNLOADING
                        )
                    }
                }
            }

            val result = streamDownloadEngine.downloadFile(
                downloadUrl = task.downloadUrl,
                tempFile = tempFile,
                expectedTotalBytes = task.totalBytes,
                callback = callback
            )

            runningJobs.remove(task.packageName)

            result.onSuccess {
                verifyAndCompleteDownload(task, tempFile, finalFile)
            }.onFailure { error ->
                val errorMsg = when {
                    error is java.net.UnknownHostException -> "שגיאת רשת: אין חיבור לאינטרנט"
                    error is java.net.SocketTimeoutException -> "שגיאת רשת: זמן תגובה ארוך מדי"
                    error is java.net.ConnectException -> "שגיאת רשת: לא ניתן להתחבר לשרת"
                    error.message?.contains("404") == true -> "שגיאה: הקובץ לא נמצא בשרת"
                    error.message?.contains("403") == true -> "שגיאה: אין הרשאת גישה לקובץ"
                    else -> "הורדה נכשלה: ${error.localizedMessage ?: error.message}"
                }
                downloadTaskDao.upsertTask(
                    task.copy(
                        status = DownloadStatus.FAILED,
                        errorMessage = errorMsg
                    )
                )
            }
            processQueue()
        }
        runningJobs[task.packageName] = job
    }

    private fun verifyAndCompleteDownload(task: DownloadTaskEntity, tempFile: File, finalFile: File) {
        scope.launch {
            downloadTaskDao.updateStatus(task.packageName, DownloadStatus.VERIFYING)
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "KosherStore:ChecksumWakeLock")

            try {
                wakeLock.acquire(5 * 60 * 1000L)
                val checksumTypeEnum = normalizeChecksumType(task.checksumType)
                val isValid = withContext(ioDispatcher) {
                    ChecksumUtils.isValid(tempFile, task.checksum, checksumTypeEnum)
                }

                if (!isValid) {
                    tempFile.delete()
                    downloadTaskDao.upsertTask(
                        task.copy(
                            status = DownloadStatus.FAILED,
                            errorMessage = context.getString(com.kosherstore.privateappstore.R.string.checksum_failed)
                        )
                    )
                    return@launch
                }

                // Rename tempFile to finalFile
                if (finalFile.exists()) finalFile.delete()
                tempFile.renameTo(finalFile)

                downloadTaskDao.upsertTask(
                    task.copy(
                        status = DownloadStatus.COMPLETED,
                        finalPath = finalFile.absolutePath,
                        downloadedBytes = finalFile.length(),
                        totalBytes = finalFile.length(),
                        speedBytesPerSec = 0L,
                        etaSeconds = 0L
                    )
                )

                if (task.autoInstall) {
                    val storeApp = StoreApp(
                        name = task.appName,
                        packageName = task.packageName,
                        versionCode = 0L,
                        versionName = "",
                        apkUrl = task.downloadUrl,
                        iconUrl = task.iconUrl,
                        description = "",
                        category = "",
                        size = "${finalFile.length() / (1024 * 1024)} MB",
                        checksum = task.checksum,
                        checksumType = checksumTypeEnum,
                        screenshots = emptyList()
                    )
                    installCoordinator.installDownloadedApk(storeApp, finalFile)
                }
            } catch (e: Exception) {
                downloadTaskDao.upsertTask(
                    task.copy(
                        status = DownloadStatus.FAILED,
                        errorMessage = "שגיאה באימות קובץ: ${e.message}"
                    )
                )
            } finally {
                if (wakeLock.isHeld) wakeLock.release()
            }
        }
    }

    private fun createTempFile(app: StoreApp): File {
        val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: context.cacheDir
        return File(folder, "${app.packageName}-${app.versionCode}.apk.tmp")
    }

    private fun createFinalFile(app: StoreApp): File {
        val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: context.cacheDir
        return File(folder, "${app.packageName}-${app.versionCode}.apk")
    }

    private fun startForegroundService() {
        runCatching {
            val intent = Intent(context, DownloadService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    private fun normalizeDriveUrl(rawUrl: String): String {
        if (!rawUrl.contains("drive.google.com") && !rawUrl.contains("drive.usercontent.google.com") && !rawUrl.contains("googleapis.com")) {
            return rawUrl
        }
        val fileId = when {
            rawUrl.contains("/file/d/") -> rawUrl.substringAfter("/file/d/").substringBefore("/")
            rawUrl.contains("/files/") -> rawUrl.substringAfter("/files/").substringBefore("?")
            rawUrl.contains("id=") -> rawUrl.substringAfter("id=").substringBefore("&")
            else -> null
        } ?: return rawUrl

        return "https://www.googleapis.com/drive/v3/files/$fileId?alt=media&key=AIzaSyDduW1Zbi2MIu8aMUMF6op72pJ1f0sPBi0"
    }

    private fun parseSizeToBytes(sizeStr: String?): Long {
        if (sizeStr.isNullOrBlank()) return 0L
        val clean = sizeStr.trim().uppercase()
        val number = clean.replace(Regex("[^0-9.]"), "").toDoubleOrNull() ?: return 0L
        return when {
            clean.contains("GB") -> (number * 1024 * 1024 * 1024).toLong()
            clean.contains("MB") -> (number * 1024 * 1024).toLong()
            clean.contains("KB") -> (number * 1024).toLong()
            clean.contains("B") -> number.toLong()
            else -> (number * 1024 * 1024).toLong()
        }
    }
}
