package com.kosherstore.privateappstore.ui.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosherstore.privateappstore.domain.model.InstallStatus
import com.kosherstore.privateappstore.domain.model.ManagementSummary
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository
import com.kosherstore.privateappstore.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ManagementViewModel @Inject constructor(
    private val repository: StoreAppRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 8)
    val events = _events.asSharedFlow()

    val uiState: StateFlow<ManagementUiState> = combine(
        repository.observeManagedApps(),
        repository.observeManagementSummary()
    ) { apps, summary ->
        ManagementUiState(apps = apps, summary = summary)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ManagementUiState()
    )

    fun onPrimaryAction(app: StoreApp) {
        viewModelScope.launch {
            when (app.installStatus) {
                InstallStatus.UPDATE_AVAILABLE -> repository.startDownload(app.packageName, autoInstall = true)
                InstallStatus.INSTALLED -> openApp(app.packageName)
                InstallStatus.NOT_INSTALLED,
                InstallStatus.FAILED -> repository.startDownload(app.packageName)
                InstallStatus.DOWNLOADED -> repository.installDownloadedApp(app.packageName)
                InstallStatus.DOWNLOADING -> repository.pauseDownload(app.packageName)
                InstallStatus.PAUSED -> repository.resumeDownload(app.packageName)
                InstallStatus.INSTALLING,
                InstallStatus.VERIFYING -> Unit
            }
        }
    }

    fun onSecondaryAction(app: StoreApp) {
        repository.buildUninstallIntent(app.packageName)?.let { intent ->
            _events.tryEmit(UiEvent.LaunchIntent(intent))
        } ?: _events.tryEmit(UiEvent.Message("אי אפשר לפתוח מסך הסרה עבור האפליקציה הזו"))
    }

    private fun openApp(packageName: String) {
        repository.buildOpenAppIntent(packageName)?.let { intent ->
            _events.tryEmit(UiEvent.LaunchIntent(intent))
        }
    }

    data class ManagementUiState(
        val apps: List<StoreApp> = emptyList(),
        val summary: ManagementSummary = ManagementSummary()
    )
}
