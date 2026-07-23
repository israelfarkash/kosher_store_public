package com.kosherstore.privateappstore.data.install

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InstallStatusReceiver : BroadcastReceiver() {

    @Inject
    lateinit var installCoordinator: InstallCoordinator

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_INSTALL_COMMIT) return

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                installCoordinator.handlePackageInstallerCallback(intent)
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val ACTION_INSTALL_COMMIT = "com.kosherstore.privateappstore.INSTALL_COMMIT"
        const val EXTRA_PACKAGE_NAME = "extra_package_name"
    }
}
