package com.kosherstore.privateappstore.domain.repository

import android.content.Intent
import com.kosherstore.privateappstore.domain.model.ManagementSummary
import com.kosherstore.privateappstore.domain.model.RepositorySyncState
import com.kosherstore.privateappstore.domain.model.StoreApp
import kotlinx.coroutines.flow.Flow

interface StoreAppRepository {
    fun observeApps(): Flow<List<StoreApp>>
    fun observeManagedApps(): Flow<List<StoreApp>>
    fun observeApp(packageName: String): Flow<StoreApp?>
    fun observeManagementSummary(): Flow<ManagementSummary>
    fun observeSyncState(): Flow<RepositorySyncState>

    suspend fun refreshApps(forceRemote: Boolean = true)
    suspend fun sync()
    suspend fun syncInstalledStatuses()
    suspend fun startDownload(packageName: String, autoInstall: Boolean = false)
    suspend fun resumeDownload(packageName: String)
    suspend fun pauseDownload(packageName: String)
    suspend fun cancelDownload(packageName: String)
    suspend fun installDownloadedApp(packageName: String)

    fun buildOpenAppIntent(packageName: String): Intent?
    suspend fun uninstallApp(packageName: String)

    suspend fun checkForUpdatesInBackground(): List<StoreApp>
}
