package com.kosherstore.privateappstore.data.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.kosherstore.privateappstore.di.IoDispatcher
import com.kosherstore.privateappstore.domain.model.ChecksumType
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        if (file.exists()) {
            file.delete()
        }

        val request = DownloadManager.Request(Uri.parse(app.apkUrl))
            .setTitle(app.name)
            .setDescription(context.getString(com.kosherstore.privateappstore.R.string.downloading_description))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                file.name
            )

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
    }

    suspend fun resumeDownload(app: StoreApp) {
        val paused = pausedDownloads[app.packageName]
        startDownload(app, paused?.autoInstall ?: false)
    }

    suspend fun pauseDownload(packageName: String) {
        val active = activeDownloads.remove(packageName) ?: return
        downloadManager.remove(active.requestId)
        pausedDownloads[packageName] = active
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
        pausedDownloads.remove(packageName)?.targetFile?.delete()
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
            activeDownloads.values.toList().forEach { active ->
                refreshDownloadState(active)
            }
            delay(800)
        }
    }

    private suspend fun refreshDownloadState(active: ActiveDownload) {
        val cursor = downloadManager.query(
            DownloadManager.Query().setFilterById(active.requestId)
        ) ?: return

        cursor.use {
            if (!it.moveToFirst()) return

            when (it.getInt(it.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))) {
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

                DownloadManager.STATUS_FAILED -> {
                    val reason = it.getInt(it.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON))
                    markFailed(active.app.packageName, "ההורדה נכשלה. קוד שגיאה: $reason")
                }
            }
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
            val isValid = withContext(ioDispatcher) {
                ChecksumUtils.isValid(
                    active.targetFile,
                    active.app.checksum,
                    active.app.checksumType
                )
            }
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
        val bytesDownloaded = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
        val totalBytes = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
        return if (totalBytes > 0) ((bytesDownloaded * 100) / totalBytes).toInt() else 0
    }

    private fun createTargetFile(app: StoreApp): File {
        val folder = requireNotNull(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
        return File(folder, "${app.packageName}-${app.versionCode}.apk")
    }

    private fun updateState(packageName: String, state: DownloadState) {
        _downloadStates.value = _downloadStates.value.toMutableMap().apply {
            put(packageName, state)
        }
    }

    private fun removeState(packageName: String) {
        _downloadStates.value = _downloadStates.value.toMutableMap().apply {
            remove(packageName)
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
}
