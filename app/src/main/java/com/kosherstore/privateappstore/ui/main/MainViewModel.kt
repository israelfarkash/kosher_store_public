package com.kosherstore.privateappstore.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosherstore.privateappstore.domain.model.InstallStatus
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository
import com.kosherstore.privateappstore.ui.common.UiEvent
import com.kosherstore.privateappstore.util.CategoryNormalizer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: StoreAppRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val selectedCategory = MutableStateFlow<String?>(null)
    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 8)
    val events = _events.asSharedFlow()

    val uiState: StateFlow<MainUiState> = combine(
        repository.observeApps(),
        repository.observeSyncState(),
        query,
        selectedCategory
    ) { apps, syncState, queryValue, categoryValue ->
        val categories = apps
            .map { CategoryNormalizer.normalize(it.category) }
            .distinct()
            .sorted()
        val effectiveCategory = categoryValue?.takeIf { it in categories }
        val filteredApps = apps.filter { app ->
            val queryMatch = queryValue.isBlank() ||
                app.name.contains(queryValue, ignoreCase = true) ||
                app.description.contains(queryValue, ignoreCase = true)
            val categoryMatch = effectiveCategory.isNullOrBlank() ||
                CategoryNormalizer.normalize(app.category) == effectiveCategory
            queryMatch && categoryMatch
        }

        MainUiState(
            isLoading = syncState.isLoading,
            isRefreshing = syncState.isRefreshing,
            apps = filteredApps,
            categories = categories,
            selectedCategory = effectiveCategory,
            query = queryValue,
            syncMessage = syncState.message
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState()
    )

    init {
        refresh(forceRemote = true)
    }

    fun refresh(forceRemote: Boolean = true) {
        viewModelScope.launch {
            repository.refreshApps(forceRemote)
            repository.syncInstalledStatuses()
        }
    }

    fun sync() {
        viewModelScope.launch {
            repository.sync()
        }
    }

    fun onSearchChanged(value: String) {
        query.value = value
    }

    fun onCategorySelected(value: String?) {
        selectedCategory.value = value?.let(CategoryNormalizer::normalize)
    }

    fun onPrimaryAction(app: StoreApp) {
        viewModelScope.launch {
            when (app.installStatus) {
                InstallStatus.NOT_INSTALLED,
                InstallStatus.UPDATE_AVAILABLE,
                InstallStatus.FAILED -> repository.startDownload(app.packageName)

                InstallStatus.DOWNLOADING -> repository.pauseDownload(app.packageName)
                InstallStatus.PAUSED -> repository.resumeDownload(app.packageName)
                InstallStatus.DOWNLOADED -> repository.installDownloadedApp(app.packageName)
                InstallStatus.INSTALLED -> openApp(app.packageName)
                InstallStatus.INSTALLING,
                InstallStatus.VERIFYING -> Unit
            }
        }
    }

    fun onSecondaryAction(app: StoreApp) {
        viewModelScope.launch {
            repository.cancelDownload(app.packageName)
        }
    }

    fun openApp(packageName: String) {
        repository.buildOpenAppIntent(packageName)?.let { intent ->
            _events.tryEmit(UiEvent.LaunchIntent(intent))
        } ?: _events.tryEmit(UiEvent.Message("לא נמצאה אפליקציה לפתיחה"))
    }

    data class MainUiState(
        val isLoading: Boolean = true,
        val isRefreshing: Boolean = false,
        val apps: List<StoreApp> = emptyList(),
        val categories: List<String> = emptyList(),
        val selectedCategory: String? = null,
        val query: String = "",
        val syncMessage: String? = null
    )
}
