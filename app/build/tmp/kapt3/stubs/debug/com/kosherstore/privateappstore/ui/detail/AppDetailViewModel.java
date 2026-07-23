package com.kosherstore.privateappstore.ui.detail;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.kosherstore.privateappstore.domain.model.InstallStatus;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository;
import com.kosherstore.privateappstore.ui.common.UiEvent;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\fJ\u000e\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\fJ\u0006\u0010\u0019\u001a\u00020\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/kosherstore/privateappstore/ui/detail/AppDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "repository", "Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;)V", "_events", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/kosherstore/privateappstore/ui/common/UiEvent;", "appState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "getAppState", "()Lkotlinx/coroutines/flow/StateFlow;", "events", "Lkotlinx/coroutines/flow/SharedFlow;", "getEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "packageName", "", "onPrimaryAction", "", "app", "onSecondaryAction", "openApp", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AppDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String packageName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> _events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.kosherstore.privateappstore.domain.model.StoreApp> appState = null;
    
    @javax.inject.Inject()
    public AppDetailViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.kosherstore.privateappstore.domain.model.StoreApp> getAppState() {
        return null;
    }
    
    public final void onPrimaryAction(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
    }
    
    public final void onSecondaryAction(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
    }
    
    public final void openApp() {
    }
}