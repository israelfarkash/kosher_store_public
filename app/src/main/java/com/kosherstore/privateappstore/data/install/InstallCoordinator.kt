package com.kosherstore.privateappstore.data.install

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import com.kosherstore.privateappstore.data.local.ManagedInstallEntity
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao
import com.kosherstore.privateappstore.di.IoDispatcher
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.util.PackageUtils
import com.kosherstore.privateappstore.util.StoreStatsStore
import com.kosherstore.privateappstore.util.XapkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

@Singleton
class InstallCoordinator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val managedInstallDao: ManagedInstallDao,
    private val packageChangeNotifier: PackageChangeNotifier,
    private val statsStore: StoreStatsStore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val pendingInstalls = ConcurrentHashMap<String, PendingInstall>()
    private val pendingUninstalls = ConcurrentHashMap<String, Long>()
    private val _events = MutableSharedFlow<InstallEvent>(extraBufferCapacity = 8)
    private val _installingPackages = MutableStateFlow<Set<String>>(emptySet())

    val events: SharedFlow<InstallEvent> = _events.asSharedFlow()
    val installingPackages: StateFlow<Set<String>> = _installingPackages.asStateFlow()

    suspend fun installDownloadedApk(app: StoreApp, apkFile: File) {
        if (!apkFile.exists()) {
            emitFailure(app.packageName, context.getString(com.kosherstore.privateappstore.R.string.apk_missing))
            return
        }

        pendingInstalls[app.packageName] = PendingInstall(app.name, app.versionCode)
        markInstalling(app.packageName, true)

        val installedByRoot = withContext(ioDispatcher) { tryRootInstall(apkFile) }
        if (installedByRoot) {
            // We don't call onPackageChanged directly here because the BroadcastReceiver will handle it
            // However, some root installs might not trigger the broadcast immediately or at all on some devices
            // But usually ACTION_PACKAGE_ADDED/REPLACED is triggered.
            // Let's call it after a small delay just in case the receiver fails
            return
        }

        val isDeviceOwner = PackageUtils.isDeviceOwner(context)
        val isXapk = withContext(ioDispatcher) { XapkUtils.isXapk(apkFile) }

        if (isDeviceOwner || isXapk) {
            val sessionStarted = withContext(ioDispatcher) { trySessionInstall(app, apkFile, isDeviceOwner) }
            if (sessionStarted) return
        }

        launchStandardInstaller(app.packageName, apkFile)
    }

    suspend fun uninstallApp(packageName: String) {
        val size = PackageUtils.getPackageCodeSize(context, packageName)
        pendingUninstalls[packageName] = size

        if (PackageUtils.isDeviceOwner(context)) {
            val packageInstaller = context.packageManager.packageInstaller
            val callbackIntent = Intent(context, InstallStatusReceiver::class.java).apply {
                action = InstallStatusReceiver.ACTION_INSTALL_COMMIT
                putExtra(InstallStatusReceiver.EXTRA_PACKAGE_NAME, packageName)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                packageName.hashCode(),
                callbackIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
            packageInstaller.uninstall(packageName, pendingIntent.intentSender)
            return
        }

        val launchIntent = Intent(Intent.ACTION_DELETE).apply {
            data = Uri.parse("package:$packageName")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (launchIntent.resolveActivity(context.packageManager) != null) {
            _events.emit(InstallEvent.LaunchSystemInstaller(launchIntent))
        } else {
            pendingUninstalls.remove(packageName)
            emitFailure(packageName, "אי אפשר לפתוח מסך הסרה עבור האפליקציה הזו")
        }
    }

    suspend fun handlePackageInstallerCallback(intent: Intent) {
        val packageName = intent.getStringExtra(InstallStatusReceiver.EXTRA_PACKAGE_NAME) ?: return
        when (intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                val confirmIntent: Intent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra(Intent.EXTRA_INTENT)
                }
                confirmIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (confirmIntent != null) {
                    _events.emit(InstallEvent.LaunchSystemInstaller(confirmIntent))
                } else {
                    emitFailure(packageName, context.getString(com.kosherstore.privateappstore.R.string.install_requires_confirmation))
                }
            }

            PackageInstaller.STATUS_SUCCESS -> onPackageChanged(packageName)

            else -> {
                val message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)
                    ?: context.getString(com.kosherstore.privateappstore.R.string.install_failed_generic)
                emitFailure(packageName, message)
            }
        }
    }

    suspend fun onPackageChanged(packageName: String) {
        val pending = pendingInstalls.remove(packageName)
        val versionCode = PackageUtils.getInstalledVersionCode(context, packageName)
        
        // ALWAYS clear installing status when package changed
        markInstalling(packageName, false)
        
        if (pending != null) {
            managedInstallDao.upsert(
                ManagedInstallEntity(
                    packageName = packageName,
                    appName = pending.appName,
                    installedVersionCode = versionCode,
                    updatedAt = System.currentTimeMillis()
                )
            )
            packageChangeNotifier.notifyChanged()
            _events.emit(InstallEvent.Success(pending.appName, packageName))
        } else {
            // Even if not pending (e.g. external install), we want to update the UI
            packageChangeNotifier.notifyChanged()
        }
    }

    suspend fun onPackageRemoved(packageName: String, replacing: Boolean) {
        if (replacing) return
        pendingInstalls.remove(packageName)
        markInstalling(packageName, false)
        managedInstallDao.delete(packageName)
        pendingUninstalls.remove(packageName)?.let(statsStore::addFreedBytes)
        packageChangeNotifier.notifyChanged()
    }

    private suspend fun launchStandardInstaller(packageName: String, apkFile: File) {
        if (!PackageUtils.canRequestPackageInstalls(context) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val settingsIntent = Intent(
                Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                Uri.parse("package:${context.packageName}")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            _events.emit(InstallEvent.Info(context.getString(com.kosherstore.privateappstore.R.string.enable_unknown_sources)))
            _events.emit(InstallEvent.LaunchSystemInstaller(settingsIntent))
            markInstalling(packageName, false)
            return
        }

        _events.emit(InstallEvent.LaunchSystemInstaller(PackageUtils.buildInstallerIntent(context, apkFile)))
    }

    private fun tryRootInstall(apkFile: File): Boolean {
        return runCatching {
            // Using the command format requested by the user
            val process = ProcessBuilder(
                "su",
                "-c",
                "pm install -r \"${apkFile.absolutePath}\""
            ).start()
            val exitCode = process.waitFor()
            exitCode == 0
        }.getOrDefault(false)
    }

    private fun trySessionInstall(app: StoreApp, apkFile: File, isDeviceOwner: Boolean): Boolean {
        return runCatching {
            val packageInstaller = context.packageManager.packageInstaller
            val sessionParams = PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL
            ).apply {
                setAppPackageName(app.packageName)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && isDeviceOwner) {
                    setRequireUserAction(PackageInstaller.SessionParams.USER_ACTION_NOT_REQUIRED)
                }
            }
            val sessionId = packageInstaller.createSession(sessionParams)
            packageInstaller.openSession(sessionId).use { session ->
                if (XapkUtils.isXapk(apkFile)) {
                    val tempDir = File(context.cacheDir, "xapk_${System.currentTimeMillis()}").apply { mkdirs() }
                    try {
                        val apkFiles = mutableListOf<File>()
                        java.util.zip.ZipFile(apkFile).use { zip ->
                            zip.entries().asSequence().forEach { entry ->
                                val entryName = entry.name
                                if (entryName.endsWith(".apk", ignoreCase = true)) {
                                    val outFile = File(tempDir, entryName.replace("/", "_"))
                                    zip.getInputStream(entry).use { input ->
                                        outFile.outputStream().use { output -> input.copyTo(output) }
                                    }
                                    apkFiles.add(outFile)
                                } else if (entryName.contains("obb/", ignoreCase = true) && entryName.endsWith(".obb", ignoreCase = true)) {
                                    try {
                                        val obbDir = File(Environment.getExternalStorageDirectory(), "Android/obb/${app.packageName}").apply { mkdirs() }
                                        val obbFile = File(obbDir, entryName.substringAfterLast("/"))
                                        zip.getInputStream(entry).use { input ->
                                            obbFile.outputStream().use { output -> input.copyTo(output) }
                                        }
                                    } catch (_: Exception) {}
                                }
                            }
                        }

                        // Ensure base.apk or main package is written first as "base.apk"
                        apkFiles.sortByDescending { 
                            it.name.contains("base", ignoreCase = true) || it.name.contains(app.packageName, ignoreCase = true)
                        }

                        apkFiles.forEachIndexed { index, file ->
                            val streamName = if (index == 0) "base.apk" else "split_$index.apk"
                            file.inputStream().use { input ->
                                session.openWrite(streamName, 0, file.length()).use { output ->
                                    input.copyTo(output)
                                    session.fsync(output)
                                }
                            }
                        }
                    } finally {
                        tempDir.deleteRecursively()
                    }
                } else {
                    apkFile.inputStream().use { input ->
                        session.openWrite("base.apk", 0, apkFile.length()).use { output ->
                            input.copyTo(output)
                            session.fsync(output)
                        }
                    }
                }
                val callbackIntent = Intent(context, InstallStatusReceiver::class.java).apply {
                    action = InstallStatusReceiver.ACTION_INSTALL_COMMIT
                    putExtra(InstallStatusReceiver.EXTRA_PACKAGE_NAME, app.packageName)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    sessionId,
                    callbackIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
                session.commit(pendingIntent.intentSender)
            }
            true
        }.getOrElse {
            false
        }
    }

    private suspend fun emitFailure(packageName: String, message: String) {
        pendingInstalls.remove(packageName)
        markInstalling(packageName, false)
        _events.emit(InstallEvent.Failure(packageName, message))
    }

    private fun markInstalling(packageName: String, active: Boolean) {
        _installingPackages.value = _installingPackages.value.toMutableSet().apply {
            if (active) add(packageName) else remove(packageName)
        }
    }

    sealed interface InstallEvent {
        data class LaunchSystemInstaller(val intent: Intent) : InstallEvent
        data class Success(val appName: String, val packageName: String) : InstallEvent
        data class Failure(val packageName: String, val message: String) : InstallEvent
        data class Info(val message: String) : InstallEvent
    }

    private data class PendingInstall(
        val appName: String,
        val versionCode: Long
    )
}
