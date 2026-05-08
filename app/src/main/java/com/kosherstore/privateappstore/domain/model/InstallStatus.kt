package com.kosherstore.privateappstore.domain.model

enum class InstallStatus {
    NOT_INSTALLED,
    DOWNLOADING,
    PAUSED,
    VERIFYING,
    DOWNLOADED,
    INSTALLING,
    INSTALLED,
    UPDATE_AVAILABLE,
    FAILED
}
