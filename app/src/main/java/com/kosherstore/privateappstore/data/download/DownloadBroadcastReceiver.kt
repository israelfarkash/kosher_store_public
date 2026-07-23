package com.kosherstore.privateappstore.data.download

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DownloadBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var downloadCoordinator: DownloadCoordinator

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != DownloadManager.ACTION_DOWNLOAD_COMPLETE) return
        val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
        if (downloadId <= 0L) return

        val pendingResult = goAsync()
        scope.launch {
            try {
                downloadCoordinator.handleSystemDownloadBroadcast(downloadId)
            } catch (e: Exception) {
                // Log or handle error
            } finally {
                pendingResult.finish()
            }
        }
    }
}
