package com.kosherstore.privateappstore.ui.management;

import androidx.lifecycle.ViewModel;
import com.kosherstore.privateappstore.domain.model.InstallStatus;
import com.kosherstore.privateappstore.domain.model.ManagementSummary;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository;
import com.kosherstore.privateappstore.ui.common.UiEvent;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0019B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001a"}, d2 = {"Lcom/kosherstore/privateappstore/ui/management/ManagementViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;", "(Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;)V", "_events", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/kosherstore/privateappstore/ui/common/UiEvent;", "events", "Lkotlinx/coroutines/flow/SharedFlow;", "getEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/kosherstore/privateappstore/ui/management/ManagementViewModel$ManagementUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "onPrimaryAction", "", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "onSecondaryAction", "openApp", "packageName", "", "ManagementUiState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ManagementViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> _events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.kosherstore.privateappstore.ui.management.ManagementViewModel.ManagementUiState> uiState = null;
    
    @javax.inject.Inject()
    public ManagementViewModel(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.kosherstore.privateappstore.ui.management.ManagementViewModel.ManagementUiState> getUiState() {
        return null;
    }
    
    public final void onPrimaryAction(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
    }
    
    public final void onSecondaryAction(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
    }
    
    private final void openApp(java.lang.String packageName) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0006H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/kosherstore/privateappstore/ui/management/ManagementViewModel$ManagementUiState;", "", "apps", "", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "summary", "Lcom/kosherstore/privateappstore/domain/model/ManagementSummary;", "(Ljava/util/List;Lcom/kosherstore/privateappstore/domain/model/ManagementSummary;)V", "getApps", "()Ljava/util/List;", "getSummary", "()Lcom/kosherstore/privateappstore/domain/model/ManagementSummary;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class ManagementUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> apps = null;
        @org.jetbrains.annotations.NotNull()
        private final com.kosherstore.privateappstore.domain.model.ManagementSummary summary = null;
        
        public ManagementUiState(@org.jetbrains.annotations.NotNull()
        java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> apps, @org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.ManagementSummary summary) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> getApps() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.domain.model.ManagementSummary getSummary() {
            return null;
        }
        
        public ManagementUiState() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.domain.model.ManagementSummary component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.ui.management.ManagementViewModel.ManagementUiState copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> apps, @org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.ManagementSummary summary) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}