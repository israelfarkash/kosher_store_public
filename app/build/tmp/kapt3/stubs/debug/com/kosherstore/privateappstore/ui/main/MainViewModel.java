package com.kosherstore.privateappstore.ui.main;

import androidx.lifecycle.ViewModel;
import com.kosherstore.privateappstore.domain.model.InstallStatus;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository;
import com.kosherstore.privateappstore.ui.common.UiEvent;
import com.kosherstore.privateappstore.util.CategoryNormalizer;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001#B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000eJ\u000e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u000eJ\u0010\u0010\u001f\u001a\u00020\u00162\b\b\u0002\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006$"}, d2 = {"Lcom/kosherstore/privateappstore/ui/main/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;", "(Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;)V", "_events", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/kosherstore/privateappstore/ui/common/UiEvent;", "events", "Lkotlinx/coroutines/flow/SharedFlow;", "getEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "query", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "selectedCategory", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/kosherstore/privateappstore/ui/main/MainViewModel$MainUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "onCategorySelected", "", "value", "onPrimaryAction", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "onSearchChanged", "onSecondaryAction", "openApp", "packageName", "refresh", "forceRemote", "", "sync", "MainUiState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> query = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> selectedCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> _events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.kosherstore.privateappstore.ui.main.MainViewModel.MainUiState> uiState = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.ui.common.UiEvent> getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.kosherstore.privateappstore.ui.main.MainViewModel.MainUiState> getUiState() {
        return null;
    }
    
    public final void refresh(boolean forceRemote) {
    }
    
    public final void sync() {
    }
    
    public final void onSearchChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onCategorySelected(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public final void onPrimaryAction(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
    }
    
    public final void onSecondaryAction(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
    }
    
    public final void openApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B[\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\tH\u00c6\u0003J_\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\tH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\tH\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\n\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0013\u0010\f\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013\u00a8\u0006#"}, d2 = {"Lcom/kosherstore/privateappstore/ui/main/MainViewModel$MainUiState;", "", "isLoading", "", "isRefreshing", "apps", "", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "categories", "", "selectedCategory", "query", "syncMessage", "(ZZLjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getApps", "()Ljava/util/List;", "getCategories", "()Z", "getQuery", "()Ljava/lang/String;", "getSelectedCategory", "getSyncMessage", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class MainUiState {
        private final boolean isLoading = false;
        private final boolean isRefreshing = false;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> apps = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> categories = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String selectedCategory = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String query = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String syncMessage = null;
        
        public MainUiState(boolean isLoading, boolean isRefreshing, @org.jetbrains.annotations.NotNull()
        java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> apps, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> categories, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedCategory, @org.jetbrains.annotations.NotNull()
        java.lang.String query, @org.jetbrains.annotations.Nullable()
        java.lang.String syncMessage) {
            super();
        }
        
        public final boolean isLoading() {
            return false;
        }
        
        public final boolean isRefreshing() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> getApps() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getCategories() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSelectedCategory() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getQuery() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSyncMessage() {
            return null;
        }
        
        public MainUiState() {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.ui.main.MainViewModel.MainUiState copy(boolean isLoading, boolean isRefreshing, @org.jetbrains.annotations.NotNull()
        java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp> apps, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> categories, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedCategory, @org.jetbrains.annotations.NotNull()
        java.lang.String query, @org.jetbrains.annotations.Nullable()
        java.lang.String syncMessage) {
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