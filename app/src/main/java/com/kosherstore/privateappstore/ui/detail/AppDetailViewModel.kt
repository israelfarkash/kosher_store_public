package com.kosherstore.privateappstore.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosherstore.privateappstore.domain.model.InstallStatus
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository
import com.kosherstore.privateappstore.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AppDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: StoreAppRepository
) : ViewModel() {

    private val packageName: String = checkNotNull(savedStateHandle["packageName"])
    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 8)
    val events = _events.asSharedFlow()

    val appState: StateFlow<StoreApp?> = repository.observeApp(packageName)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun onPrimaryAction(app: StoreApp) {
        viewModelScope.launch {
            when (app.installStatus) {
                InstallStatus.NOT_INSTALLED,
                InstallStatus.UPDATE_AVAILABLE,
                InstallStatus.FAILED -> repository.startDownload(app.packageName)

                InstallStatus.DOWNLOADING -> repository.pauseDownload(app.packageName)
                InstallStatus.PAUSED -> repository.resumeDownload(app.packageName)
                InstallStatus.DOWNLOADED -> repository.installDownloadedApp(app.packageName)
                InstallStatus.INSTALLED -> openApp()
                InstallStatus.INSTALLING,
                InstallStatus.VERIFYING -> Unit
            }
        }
    }

    fun onSecondaryAction(app: StoreApp) {
        viewModelScope.launch { repository.cancelDownload(app.packageName) }
    }

    fun openApp() {
        repository.buildOpenAppIntent(packageName)?.let {
            _events.tryEmit(UiEvent.LaunchIntent(it))
        }
    }
}
