package com.kosherstore.privateappstore.domain.model

enum class DownloadStatus {
    IDLE,
    PENDING,
    RUNNING,
    PAUSED,
    VERIFYING,
    COMPLETED,
    FAILED
}
