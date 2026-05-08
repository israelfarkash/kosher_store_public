package com.kosherstore.privateappstore.domain.model

data class StoreApp(
    val name: String,
    val packageName: String,
    val versionCode: Long,
    val versionName: String? = null,
    val apkUrl: String,
    val iconUrl: String,
    val description: String,
    val category: String,
    val size: String,
    val checksum: String,
    val checksumType: ChecksumType = ChecksumType.SHA256,
    val installedVersionCode: Long? = null,
    val installStatus: InstallStatus = InstallStatus.NOT_INSTALLED,
    val downloadState: DownloadState = DownloadState(),
    val isManagedByStore: Boolean = false
) {
    val isInstalled: Boolean
        get() = installedVersionCode != null
}
