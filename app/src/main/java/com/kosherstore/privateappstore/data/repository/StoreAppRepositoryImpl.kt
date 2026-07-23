package com.kosherstore.privateappstore.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kosherstore.privateappstore.BuildConfig
import com.kosherstore.privateappstore.data.download.DownloadCoordinator
import com.kosherstore.privateappstore.data.local.AppEntity
import com.kosherstore.privateappstore.data.local.dao.AppDao
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao
import com.kosherstore.privateappstore.data.mapper.normalizeChecksumType
import com.kosherstore.privateappstore.data.mapper.toEntity
import com.kosherstore.privateappstore.data.remote.AppsApiService
import com.kosherstore.privateappstore.data.remote.dto.StoreAppDto
import com.kosherstore.privateappstore.data.install.InstallCoordinator
import com.kosherstore.privateappstore.data.install.PackageChangeNotifier
import com.kosherstore.privateappstore.di.IoDispatcher
import com.kosherstore.privateappstore.domain.model.DownloadState
import com.kosherstore.privateappstore.domain.model.DownloadStatus
import com.kosherstore.privateappstore.domain.model.InstallStatus
import com.kosherstore.privateappstore.domain.model.ManagementSummary
import com.kosherstore.privateappstore.domain.model.RepositorySyncState
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.domain.model.SyncSource
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository
import com.kosherstore.privateappstore.util.PackageUtils
import com.kosherstore.privateappstore.util.StoreStatsStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@Singleton
class StoreAppRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appDao: AppDao,
    private val managedInstallDao: ManagedInstallDao,
    private val appsApiService: AppsApiService,
    private val gson: Gson,
    private val downloadCoordinator: DownloadCoordinator,
    private val installCoordinator: InstallCoordinator,
    private val packageChangeNotifier: PackageChangeNotifier,
    private val statsStore: StoreStatsStore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : StoreAppRepository {

    private val syncState = MutableStateFlow(RepositorySyncState())

    override fun observeApps(): Flow<List<StoreApp>> {
        return combine(
            appDao.observeApps(),
            downloadCoordinator.downloadStates,
            installCoordinator.installingPackages,
            managedInstallDao.observeManagedApps(),
            packageChangeNotifier.changes
        ) { entities, downloads, installing, managed, _ ->
            val managedPackages = managed.associateBy { it.packageName }
            entities.map { entity ->
                entity.toDomain(
                    context = context,
                    downloadState = downloads[entity.packageName],
                    isInstalling = entity.packageName in installing,
                    isManaged = entity.packageName in managedPackages
                )
            }
        }
    }

    override fun observeManagedApps(): Flow<List<StoreApp>> {
        return observeApps().map { apps ->
            apps.filter { it.isInstalled || it.isManagedByStore }
        }
    }

    override fun observeApp(packageName: String): Flow<StoreApp?> {
        return observeApps().map { apps -> apps.firstOrNull { it.packageName == packageName } }
    }

    override fun observeManagementSummary(): Flow<ManagementSummary> {
        return combine(observeManagedApps(), statsStore.freedBytes) { managedApps, freedBytes ->
            ManagementSummary(
                installedCount = managedApps.count { it.isInstalled },
                freedBytes = freedBytes
            )
        }
    }

    override fun observeSyncState(): Flow<RepositorySyncState> = syncState

    override suspend fun refreshApps(forceRemote: Boolean) {
        syncState.value = syncState.value.copy(
            isLoading = syncState.value.isLoading,
            isRefreshing = true,
            message = null
        )

        val cached = appDao.getAll()
        if (!forceRemote && cached.isNotEmpty()) {
            syncState.value = RepositorySyncState(
                isLoading = false,
                isRefreshing = false,
                source = SyncSource.CACHE
            )
            return
        }

        runCatching {
            val url = if (forceRemote) {
                "${BuildConfig.APPS_JSON_URL}?v=${System.currentTimeMillis()}"
            } else {
                BuildConfig.APPS_JSON_URL
            }
            val remote = appsApiService.fetchApps(url)
            appDao.replaceAll(remote.map { it.toEntity() })
            syncState.value = RepositorySyncState(
                isLoading = false,
                isRefreshing = false,
                source = SyncSource.NETWORK
            )
        }.recoverCatching {
            if (cached.isNotEmpty()) {
                syncState.value = RepositorySyncState(
                    isLoading = false,
                    isRefreshing = false,
                    source = SyncSource.CACHE,
                    message = toReadableMessage(it)
                )
            } else {
                val seeded = loadSeedApps()
                appDao.replaceAll(seeded.map { dto -> dto.toEntity() })
                syncState.value = RepositorySyncState(
                    isLoading = false,
                    isRefreshing = false,
                    source = SyncSource.SEED,
                    message = toReadableMessage(it)
                )
            }
        }
    }

    override suspend fun syncInstalledStatuses() {
        packageChangeNotifier.notifyChanged()
    }

    override suspend fun sync() {
        refreshApps(forceRemote = true)
        syncInstalledStatuses()
    }

    override suspend fun startDownload(packageName: String, autoInstall: Boolean) {
        val app = findApp(packageName) ?: return
        downloadCoordinator.startDownload(app, autoInstall)
    }

    override suspend fun resumeDownload(packageName: String) {
        val app = findApp(packageName) ?: return
        downloadCoordinator.resumeDownload(app)
    }

    override suspend fun pauseDownload(packageName: String) {
        downloadCoordinator.pauseDownload(packageName)
    }

    override suspend fun cancelDownload(packageName: String) {
        downloadCoordinator.cancelDownload(packageName)
    }

    override suspend fun installDownloadedApp(packageName: String) {
        val app = findApp(packageName) ?: return
        val apkFile = downloadCoordinator.getDownloadedFile(packageName) ?: return
        installCoordinator.installDownloadedApk(app, apkFile)
    }

    override fun buildOpenAppIntent(packageName: String) = PackageUtils.getLaunchIntent(context, packageName)

    override suspend fun uninstallApp(packageName: String) = installCoordinator.uninstallApp(packageName)

    override suspend fun checkForUpdatesInBackground(): List<StoreApp> = withContext(ioDispatcher) {
        // No-op for updates since they are managed externally
        emptyList()
    }

    private suspend fun findApp(packageName: String): StoreApp? {
        return withContext(ioDispatcher) {
            appDao.getAll()
                .firstOrNull { it.packageName == packageName }
                ?.toDomain(context, downloadCoordinator.downloadStates.value[packageName])
        }
    }

    private suspend fun loadSeedApps(): List<StoreAppDto> = withContext(ioDispatcher) {
        val seedJson = context.assets.open("apps_seed.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<StoreAppDto>>() {}.type
        gson.fromJson<List<StoreAppDto>>(seedJson, type)
    }

    private fun toReadableMessage(error: Throwable): String {
        return when (error) {
            is UnknownHostException -> context.getString(com.kosherstore.privateappstore.R.string.error_no_internet)
            is SocketTimeoutException -> context.getString(com.kosherstore.privateappstore.R.string.error_timeout)
            is HttpException -> context.getString(com.kosherstore.privateappstore.R.string.error_server, error.code())
            else -> context.getString(com.kosherstore.privateappstore.R.string.error_generic)
        }
    }

    private fun AppEntity.toDomain(
        context: Context,
        downloadState: DownloadState? = null,
        isInstalling: Boolean = false,
        isManaged: Boolean = false
    ): StoreApp {
        val installedVersionCode = PackageUtils.getInstalledVersionCode(context, packageName)
        val effectiveDownloadState = downloadState ?: DownloadState()
        val installStatus = when {
            isInstalling -> InstallStatus.INSTALLING
            effectiveDownloadState.status == DownloadStatus.RUNNING || effectiveDownloadState.status == DownloadStatus.PENDING -> InstallStatus.DOWNLOADING
            effectiveDownloadState.status == DownloadStatus.PAUSED -> InstallStatus.PAUSED
            effectiveDownloadState.status == DownloadStatus.VERIFYING -> InstallStatus.VERIFYING
            effectiveDownloadState.status == DownloadStatus.COMPLETED -> InstallStatus.DOWNLOADED
            effectiveDownloadState.status == DownloadStatus.FAILED -> InstallStatus.FAILED
            installedVersionCode == null -> InstallStatus.NOT_INSTALLED
            else -> InstallStatus.INSTALLED
        }

        return StoreApp(
            name = name,
            packageName = packageName,
            versionCode = versionCode,
            versionName = versionName,
            apkUrl = apkUrl,
            iconUrl = iconUrl,
            description = description,
            category = category,
            size = size,
            checksum = checksum,
            checksumType = normalizeChecksumType(checksumType),
            screenshots = getScreenshots(),
            installedVersionCode = installedVersionCode,
            installStatus = installStatus,
            downloadState = effectiveDownloadState,
            isManagedByStore = isManaged
        )
    }
}
