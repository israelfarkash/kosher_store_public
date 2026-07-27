package com.kosherstore.privateappstore.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosherstore.privateappstore.domain.model.InstallStatus
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository
import com.kosherstore.privateappstore.util.CategoryNormalizer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: StoreAppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val categoryName: String = savedStateHandle.get<String>("categoryName") ?: ""

    val appsState: StateFlow<List<StoreApp>> = repository.observeApps()
        .map { apps ->
            apps.filter { CategoryNormalizer.normalize(it.category) == categoryName }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onPrimaryAction(
        app: StoreApp,
        launchIntentConsumer: (android.content.Intent) -> Unit,
        messageConsumer: (String) -> Unit
    ) {
        viewModelScope.launch {
            when (app.installStatus) {
                InstallStatus.NOT_INSTALLED,
                InstallStatus.UPDATE_AVAILABLE,
                InstallStatus.FAILED -> repository.startDownload(app.packageName, autoInstall = true)

                InstallStatus.DOWNLOADING -> repository.pauseDownload(app.packageName)
                InstallStatus.PAUSED -> repository.resumeDownload(app.packageName)
                InstallStatus.DOWNLOADED -> repository.installDownloadedApp(app.packageName)
                InstallStatus.INSTALLED -> {
                    val intent = repository.buildOpenAppIntent(app.packageName)
                    if (intent != null) {
                        launchIntentConsumer(intent)
                    } else {
                        messageConsumer("לא נמצאה אפליקציה לפתיחה")
                    }
                }
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
}
