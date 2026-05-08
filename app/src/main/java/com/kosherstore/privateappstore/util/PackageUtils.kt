package com.kosherstore.privateappstore.util

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.core.content.pm.PackageInfoCompat
import java.io.File

object PackageUtils {

    fun getInstalledVersionCode(context: Context, packageName: String): Long? {
        return runCatching {
            val info = context.packageManager.getPackageInfoCompat(packageName)
            PackageInfoCompat.getLongVersionCode(info)
        }.getOrNull()
    }

    fun getLaunchIntent(context: Context, packageName: String): Intent? {
        return context.packageManager.getLaunchIntentForPackage(packageName)
    }

    fun getPackageCodeSize(context: Context, packageName: String): Long {
        return runCatching {
            val info = context.packageManager.getPackageInfoCompat(packageName)
            info.applicationInfo?.sourceDir?.let(::File)?.length() ?: 0L
        }.getOrDefault(0L)
    }

    fun isDeviceOwner(context: Context): Boolean {
        val manager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return manager.isDeviceOwnerApp(context.packageName)
    }

    fun canRequestPackageInstalls(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.packageManager.canRequestPackageInstalls()
        } else {
            true
        }
    }

    fun buildInstallerIntent(context: Context, apkFile: File): Intent {
        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            apkFile
        )
        return Intent(Intent.ACTION_INSTALL_PACKAGE).apply {
            data = contentUri
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
    }

    @Suppress("DEPRECATION")
    private fun PackageManager.getPackageInfoCompat(packageName: String): PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            getPackageInfo(packageName, 0)
        }
    }
}
