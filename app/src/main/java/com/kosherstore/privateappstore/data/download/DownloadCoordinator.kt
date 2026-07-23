package com.kosherstore.privateappstore.data.download

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.PowerManager
import com.kosherstore.privateappstore.di.IoDispatcher
import com.kosherstore.privateappstore.domain.model.DownloadState
import com.kosherstore.privateappstore.domain.model.DownloadStatus
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.data.install.InstallCoordinator
import com.kosherstore.privateappstore.util.ChecksumUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Singleton
class DownloadCoordinator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val downloadManager: DownloadManager,
    private val installCoordinator: InstallCoordinator,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)
    private val activeDownloads = ConcurrentHashMap<String, ActiveDownload>()
    private val pausedDownloads = ConcurrentHashMap<String, ActiveDownload>()
    private val _downloadStates = MutableStateFlow<Map<String, DownloadState>>(emptyMap())
    val downloadStates: StateFlow<Map<String, DownloadState>> = _downloadStates.asStateFlow()

    init {
        scope.launch { pollActiveDownloads() }
    }

    suspend fun startDownload(app: StoreApp, autoInstall: Boolean = false) {
        if (activeDownloads.containsKey(app.packageName)) return

        val file = createTargetFile(app)
        runCatching {
            if (file.exists()) {
                file.delete()
            }
            
            val downloadUrl = normalizeDriveUrl(app.apkUrl)
            val request = DownloadManager.Request(Uri.parse(downloadUrl))
                .setTitle(app.name)
                .setDescription(context.getString(com.kosherstore.privateappstore.R.string.downloading_description))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
                // Add common headers to improve compatibility with some servers (like Google Drive)
                .addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Mobile Safari/537.36")

            // Ensure destination is set correctly based on the folder availability
            val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            if (folder != null) {
                request.setDestinationInExternalFilesDir(
                    context,
                    Environment.DIRECTORY_DOWNLOADS,
                    file.name
                )
            } else {
                // Fallback to internal cache if external is not available (less reliable for DownloadManager)
                request.setDestinationUri(Uri.fromFile(file))
            }

            val requestId = downloadManager.enqueue(request)
            pausedDownloads.remove(app.packageName)
            activeDownloads[app.packageName] = ActiveDownload(
                app = app,
                requestId = requestId,
                targetFile = file,
                autoInstall = autoInstall,
                progress = 0
            )
            updateState(
                app.packageName,
                DownloadState(
                    requestId = requestId,
                    progress = 0,
                    status = DownloadStatus.PENDING,
                    autoInstall = autoInstall
                )
            )
            startService()
        }.onFailure {
            markFailed(app.packageName, "לא ניתן להתחיל הורדה: ${it.message}")
        }
    }

    suspend fun resumeDownload(app: StoreApp) {
        val paused = pausedDownloads.remove(app.packageName)
        activeDownloads.remove(app.packageName)
        startDownload(app, paused?.autoInstall ?: false)
    }

    suspend fun pauseDownload(packageName: String) {
        val active = activeDownloads.remove(packageName) ?: return
        downloadManager.remove(active.requestId)
        pausedDownloads[packageName] = active.copy(progress = active.progress)
        updateState(
            packageName,
            DownloadState(
                progress = active.progress,
                status = DownloadStatus.PAUSED,
                autoInstall = active.autoInstall
            )
        )
    }

    suspend fun cancelDownload(packageName: String) {
        activeDownloads.remove(packageName)?.let {
            downloadManager.remove(it.requestId)
            it.targetFile.delete()
        }
        pausedDownloads.remove(packageName)?.let {
            it.targetFile.delete()
        }
        removeState(packageName)
    }

    suspend fun clear(packageName: String) {
        activeDownloads.remove(packageName)
        pausedDownloads.remove(packageName)
        removeState(packageName)
    }

    fun getDownloadedFile(packageName: String): File? {
        val state = _downloadStates.value[packageName] ?: return null
        return state.localFilePath?.let(::File)?.takeIf { it.exists() }
    }

    suspend fun handleSystemDownloadBroadcast(downloadId: Long) {
        val download = activeDownloads.values.firstOrNull { it.requestId == downloadId } ?: return
        refreshDownloadState(download)
    }

    private suspend fun pollActiveDownloads() {
        while (true) {
            val currentActive = activeDownloads.values.toList()
            currentActive.forEach { active ->
                refreshDownloadState(active)
            }
            delay(1000)
        }
    }

    private suspend fun refreshDownloadState(active: ActiveDownload) {
        runCatching {
            val cursor = downloadManager.query(
                DownloadManager.Query().setFilterById(active.requestId)
            )

            if (cursor == null || !cursor.moveToFirst()) {
                // If the download is no longer in DownloadManager, it failed or was removed
                cursor?.close()
                if (active.progress < 100 && !active.isVerifying) {
                    markFailed(active.app.packageName, "ההורדה הופסקה על ידי המערכת")
                }
                return@runCatching
            }

            cursor.use { it ->
                val statusIndex = it.getColumnIndex(DownloadManager.COLUMN_STATUS)
                if (statusIndex == -1) return@use

                when (it.getInt(statusIndex)) {
                    DownloadManager.STATUS_PENDING -> {
                        updateRunningState(active, DownloadStatus.PENDING, readProgress(it))
                    }

                    DownloadManager.STATUS_RUNNING -> {
                        updateRunningState(active, DownloadStatus.RUNNING, readProgress(it))
                    }

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        if (!active.isVerifying) {
                            active.isVerifying = true
                            updateState(
                                active.app.packageName,
                                DownloadState(
                                    requestId = active.requestId,
                                    progress = 100,
                                    status = DownloadStatus.VERIFYING,
                                    autoInstall = active.autoInstall
                                )
                            )
                            verifyFile(active)
                        }
                    }

                    DownloadManager.STATUS_PAUSED -> {
                        updateRunningState(active, DownloadStatus.PAUSED, readProgress(it))
                    }

                    DownloadManager.STATUS_FAILED -> {
                        val reasonIndex = it.getColumnIndex(DownloadManager.COLUMN_REASON)
                        val reason = if (reasonIndex != -1) it.getInt(reasonIndex) else -1
                        markFailed(active.app.packageName, "ההורדה נכשלה (קוד: $reason)")
                    }
                }
            }
        }.onFailure {
            // Log error but keep polling
        }
    }

    private fun updateRunningState(active: ActiveDownload, status: DownloadStatus, progress: Int) {
        active.progress = progress
        updateState(
            active.app.packageName,
            DownloadState(
                requestId = active.requestId,
                progress = progress,
                status = status,
                autoInstall = active.autoInstall
            )
        )
    }

    private fun verifyFile(active: ActiveDownload) {
        scope.launch {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PrivateStore:ChecksumWakeLock")
            
            try {
                wakeLock.acquire(5 * 60 * 1000L /*5 minutes*/)
                val isValid = withContext(ioDispatcher) {
                    ChecksumUtils.isValid(
                        active.targetFile,
                        active.app.checksum,
                        active.app.checksumType
                    )
                }
                
                // Final removal from active set
                activeDownloads.remove(active.app.packageName)
                
                if (!isValid) {
                    active.targetFile.delete()
                    markFailed(active.app.packageName, context.getString(com.kosherstore.privateappstore.R.string.checksum_failed))
                    return@launch
                }

                updateState(
                    active.app.packageName,
                    DownloadState(
                        progress = 100,
                        status = DownloadStatus.COMPLETED,
                        localFilePath = active.targetFile.absolutePath,
                        autoInstall = active.autoInstall
                    )
                )

                if (active.autoInstall) {
                    installCoordinator.installDownloadedApk(active.app, active.targetFile)
                }
            } catch (e: Exception) {
                activeDownloads.remove(active.app.packageName)
                markFailed(active.app.packageName, "שגיאה באימות הקובץ: ${e.message}")
            } finally {
                if (wakeLock.isHeld) wakeLock.release()
            }
        }
    }

    private fun markFailed(packageName: String, message: String) {
        activeDownloads.remove(packageName)?.targetFile?.delete()
        updateState(
            packageName,
            DownloadState(
                status = DownloadStatus.FAILED,
                errorMessage = message
            )
        )
    }

    private fun readProgress(cursor: android.database.Cursor): Int {
        val downloadedIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
        val totalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
        
        if (downloadedIndex == -1 || totalIndex == -1) return 0
        
        val bytesDownloaded = cursor.getLong(downloadedIndex)
        val totalBytes = cursor.getLong(totalIndex)
        return if (totalBytes > 0) ((bytesDownloaded * 100) / totalBytes).toInt() else 0
    }

    private fun createTargetFile(app: StoreApp): File {
        val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) 
            ?: context.cacheDir
        return File(folder, "${app.packageName}-${app.versionCode}.apk")
    }

    private fun updateState(packageName: String, state: DownloadState) {
        _downloadStates.update { current ->
            current.toMutableMap().apply {
                put(packageName, state)
            }
        }
    }

    private fun removeState(packageName: String) {
        _downloadStates.update { current ->
            current.toMutableMap().apply {
                remove(packageName)
            }
        }
    }

    private fun startService() {
        runCatching {
            val intent = Intent(context, DownloadService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    private data class ActiveDownload(
        val app: StoreApp,
        val requestId: Long,
        val targetFile: File,
        val autoInstall: Boolean,
        var progress: Int,
        var isVerifying: Boolean = false
    )

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

        return "https://drive.usercontent.google.com/download?id=$fileId&confirm=t&export=download"
    }
}
