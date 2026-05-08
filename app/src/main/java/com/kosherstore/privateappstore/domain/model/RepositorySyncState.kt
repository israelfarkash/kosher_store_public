package com.kosherstore.privateappstore.domain.model

enum class SyncSource {
    NETWORK,
    CACHE,
    SEED
}

data class RepositorySyncState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val source: SyncSource = SyncSource.NETWORK,
    val message: String? = null
)
