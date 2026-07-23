package com.kosherstore.privateappstore.ui.common

import android.content.Context
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.domain.model.InstallStatus
import com.kosherstore.privateappstore.domain.model.StoreApp

object AppPresentationUtils {
    
    fun primaryActionLabelRes(app: StoreApp): Int {
        return when (app.installStatus) {
            InstallStatus.NOT_INSTALLED -> R.string.action_download
            InstallStatus.DOWNLOADING -> R.string.action_pause
            InstallStatus.PAUSED -> R.string.action_resume
            InstallStatus.VERIFYING -> R.string.action_verifying
            InstallStatus.DOWNLOADED -> R.string.action_install
            InstallStatus.INSTALLING -> R.string.action_installing
            InstallStatus.INSTALLED -> R.string.action_open
            InstallStatus.FAILED -> R.string.action_retry
            InstallStatus.UPDATE_AVAILABLE -> R.string.action_download // Should not happen
        }
    }

    fun isPrimaryEnabled(app: StoreApp): Boolean {
        return app.installStatus != InstallStatus.INSTALLING &&
               app.installStatus != InstallStatus.VERIFYING
    }

    fun secondaryActionLabelRes(app: StoreApp, mode: AppCardMode): Int? {
        return when (app.installStatus) {
            InstallStatus.DOWNLOADING, InstallStatus.PAUSED -> R.string.action_cancel
            InstallStatus.INSTALLED, InstallStatus.UPDATE_AVAILABLE -> {
                if (mode == AppCardMode.MANAGEMENT) R.string.action_uninstall else null
            }
            else -> null
        }
    }

    fun shouldShowProgress(app: StoreApp): Boolean {
        return app.installStatus == InstallStatus.DOWNLOADING ||
               app.installStatus == InstallStatus.PAUSED
    }

    fun statusColor(context: Context, app: StoreApp): Int {
        val colorRes = when (app.installStatus) {
            InstallStatus.INSTALLED -> R.color.md_theme_light_outline
            InstallStatus.DOWNLOADING, InstallStatus.PAUSED, InstallStatus.VERIFYING, InstallStatus.DOWNLOADED, InstallStatus.INSTALLING -> 
                R.color.md_theme_light_primary
            InstallStatus.FAILED -> R.color.md_theme_light_error
            else -> R.color.md_theme_light_outline
        }
        return context.getColor(colorRes)
    }

    fun statusText(context: Context, app: StoreApp): String {
        return when (app.installStatus) {
            InstallStatus.NOT_INSTALLED -> context.getString(R.string.status_not_installed)
            InstallStatus.DOWNLOADING -> context.getString(R.string.status_downloading, app.downloadState.progress)
            InstallStatus.PAUSED -> context.getString(R.string.status_paused, app.downloadState.progress)
            InstallStatus.VERIFYING -> context.getString(R.string.status_verifying)
            InstallStatus.DOWNLOADED -> context.getString(R.string.status_ready_to_install)
            InstallStatus.INSTALLING -> context.getString(R.string.status_installing)
            InstallStatus.INSTALLED -> context.getString(R.string.status_installed)
            InstallStatus.FAILED -> app.downloadState.errorMessage ?: context.getString(R.string.status_failed)
            InstallStatus.UPDATE_AVAILABLE -> context.getString(R.string.status_installed) // Fallback
        }
    }
}
