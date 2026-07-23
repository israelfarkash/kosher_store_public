package com.kosherstore.privateappstore.data.download

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import com.kosherstore.privateappstore.util.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DownloadService : Service() {

    @Inject
    lateinit var downloadCoordinator: DownloadCoordinator

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        observeDownloads()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationHelper.buildDownloadForegroundNotification(this, "")
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NotificationHelper.DOWNLOAD_SERVICE_NOTIFICATION_ID, 
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(NotificationHelper.DOWNLOAD_SERVICE_NOTIFICATION_ID, notification)
        }
        
        return START_STICKY
    }

    private fun observeDownloads() {
        serviceScope.launch {
            downloadCoordinator.downloadStates.collectLatest { states ->
                val activeCount = states.values.count { 
                    it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.RUNNING ||
                    it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.PENDING ||
                    it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.VERIFYING
                }

                if (activeCount > 0) {
                    val message = if (activeCount == 1) {
                        val active = states.values.first { 
                            it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.RUNNING ||
                            it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.PENDING ||
                            it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.VERIFYING
                        }
                        "מוריד אפליקציה (${active.progress}%)"
                    } else {
                        "מוריד $activeCount אפליקציות"
                    }
                    
                    val notification = NotificationHelper.buildDownloadForegroundNotification(this@DownloadService, message)
                    NotificationHelper.updateNotification(this@DownloadService, NotificationHelper.DOWNLOAD_SERVICE_NOTIFICATION_ID, notification)
                } else {
                    // Small delay before stopping to prevent flickering on quick transitions
                    kotlinx.coroutines.delay(2000)
                    val stillActive = downloadCoordinator.downloadStates.value.values.any { 
                        it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.RUNNING ||
                        it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.PENDING ||
                        it.status == com.kosherstore.privateappstore.domain.model.DownloadStatus.VERIFYING
                    }
                    if (!stillActive) {
                        stopForeground(STOP_FOREGROUND_REMOVE)
                        stopSelf()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
