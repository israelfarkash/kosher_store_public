package com.kosherstore.privateappstore.domain.model

data class DownloadState(
    val requestId: Long? = null,
    val progress: Int = 0,
    val status: DownloadStatus = DownloadStatus.IDLE,
    val localFilePath: String? = null,
    val errorMessage: String? = null,
    val autoInstall: Boolean = false
)
