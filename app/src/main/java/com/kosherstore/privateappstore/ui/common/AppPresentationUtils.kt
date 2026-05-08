package com.kosherstore.privateappstore.ui.common

import android.content.Context
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.domain.model.InstallStatus
import com.kosherstore.privateappstore.domain.model.StoreApp

object AppPresentationUtils {

    fun primaryActionLabelRes(app: StoreApp): Int {
        return when (app.installStatus) {
            InstallStatus.NOT_INSTALLED -> R.string.action_download
            InstallStatus.UPDATE_AVAILABLE -> R.string.action_update
            InstallStatus.DOWNLOADING -> R.string.action_pause
            InstallStatus.PAUSED -> R.string.action_resume
            InstallStatus.VERIFYING -> R.string.action_verifying
            InstallStatus.DOWNLOADED -> R.string.action_install
            InstallStatus.INSTALLING -> R.string.action_installing
            InstallStatus.INSTALLED -> R.string.action_open
            InstallStatus.FAILED -> R.string.action_retry
        }
    }

    fun secondaryActionLabelRes(app: StoreApp, mode: AppCardMode): Int? {
        return when (mode) {
            AppCardMode.STORE -> when (app.installStatus) {
                InstallStatus.DOWNLOADING,
                InstallStatus.PAUSED,
                InstallStatus.DOWNLOADED -> R.string.action_cancel

                else -> null
            }

            AppCardMode.MANAGEMENT -> if (app.isInstalled) R.string.action_uninstall else null
        }
    }

    fun isPrimaryEnabled(app: StoreApp): Boolean {
        return app.installStatus != InstallStatus.VERIFYING && app.installStatus != InstallStatus.INSTALLING
    }

    fun shouldShowProgress(app: StoreApp): Boolean {
        return when (app.installStatus) {
            InstallStatus.DOWNLOADING,
            InstallStatus.PAUSED,
            InstallStatus.VERIFYING -> true

            else -> false
        }
    }

    fun statusText(context: Context, app: StoreApp): String {
        return when (app.installStatus) {
            InstallStatus.NOT_INSTALLED -> context.getString(R.string.status_not_installed)
            InstallStatus.UPDATE_AVAILABLE -> context.getString(R.string.status_update_available)
            InstallStatus.DOWNLOADING -> context.getString(R.string.status_downloading, app.downloadState.progress)
            InstallStatus.PAUSED -> context.getString(R.string.status_paused, app.downloadState.progress)
            InstallStatus.VERIFYING -> context.getString(R.string.status_verifying)
            InstallStatus.DOWNLOADED -> context.getString(R.string.status_ready_to_install)
            InstallStatus.INSTALLING -> context.getString(R.string.status_installing)
            InstallStatus.INSTALLED -> context.getString(R.string.status_installed)
            InstallStatus.FAILED -> app.downloadState.errorMessage ?: context.getString(R.string.status_failed)
        }
    }
}
