package com.kosherstore.privateappstore.data.install

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kosherstore.privateappstore.data.download.DownloadCoordinator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PackageChangeReceiver : BroadcastReceiver() {

    @Inject
    lateinit var installCoordinator: InstallCoordinator

    @Inject
    lateinit var downloadCoordinator: DownloadCoordinator

    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.data?.schemeSpecificPart ?: return
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (intent.action) {
                    Intent.ACTION_PACKAGE_ADDED,
                    Intent.ACTION_PACKAGE_REPLACED -> {
                        downloadCoordinator.clear(packageName)
                        installCoordinator.onPackageChanged(packageName)
                    }

                    Intent.ACTION_PACKAGE_REMOVED -> {
                        downloadCoordinator.clear(packageName)
                        installCoordinator.onPackageRemoved(
                            packageName,
                            intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)
                        )
                    }
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
