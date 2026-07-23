package com.kosherstore.privateappstore.domain.repository;

import android.content.Intent;
import com.kosherstore.privateappstore.domain.model.ManagementSummary;
import com.kosherstore.privateappstore.domain.model.RepositorySyncState;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00a6@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u000fH&J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u000fH&J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000fH&J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000fH&J\u0016\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\u0017\u001a\u00020\u00072\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u00a6@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\bJ \u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001d\u001a\u00020\u0019H\u00a6@\u00a2\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010 \u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010!\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\""}, d2 = {"Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;", "", "buildOpenAppIntent", "Landroid/content/Intent;", "packageName", "", "cancelDownload", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkForUpdatesInBackground", "", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "installDownloadedApp", "observeApp", "Lkotlinx/coroutines/flow/Flow;", "observeApps", "observeManagedApps", "observeManagementSummary", "Lcom/kosherstore/privateappstore/domain/model/ManagementSummary;", "observeSyncState", "Lcom/kosherstore/privateappstore/domain/model/RepositorySyncState;", "pauseDownload", "refreshApps", "forceRemote", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resumeDownload", "startDownload", "autoInstall", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sync", "syncInstalledStatuses", "uninstallApp", "app_debug"})
public abstract interface StoreAppRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp>> observeApps();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp>> observeManagedApps();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.kosherstore.privateappstore.domain.model.StoreApp> observeApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.kosherstore.privateappstore.domain.model.ManagementSummary> observeManagementSummary();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.kosherstore.privateappstore.domain.model.RepositorySyncState> observeSyncState();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object refreshApps(boolean forceRemote, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object syncInstalledStatuses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, boolean autoInstall, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object resumeDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object pauseDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object cancelDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object installDownloadedApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract android.content.Intent buildOpenAppIntent(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object uninstallApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object checkForUpdatesInBackground(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}