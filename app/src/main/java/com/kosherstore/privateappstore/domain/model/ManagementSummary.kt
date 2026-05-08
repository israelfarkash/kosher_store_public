package com.kosherstore.privateappstore.domain.model

data class ManagementSummary(
    val installedCount: Int = 0,
    val pendingUpdates: Int = 0,
    val freedBytes: Long = 0L
)
