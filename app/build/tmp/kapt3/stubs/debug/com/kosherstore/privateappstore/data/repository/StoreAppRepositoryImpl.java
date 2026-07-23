package com.kosherstore.privateappstore.data.repository;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosherstore.privateappstore.BuildConfig;
import com.kosherstore.privateappstore.data.download.DownloadCoordinator;
import com.kosherstore.privateappstore.data.local.AppEntity;
import com.kosherstore.privateappstore.data.local.dao.AppDao;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;
import com.kosherstore.privateappstore.data.remote.AppsApiService;
import com.kosherstore.privateappstore.data.remote.dto.StoreAppDto;
import com.kosherstore.privateappstore.data.install.InstallCoordinator;
import com.kosherstore.privateappstore.data.install.PackageChangeNotifier;
import com.kosherstore.privateappstore.di.IoDispatcher;
import com.kosherstore.privateappstore.domain.model.DownloadState;
import com.kosherstore.privateappstore.domain.model.DownloadStatus;
import com.kosherstore.privateappstore.domain.model.InstallStatus;
import com.kosherstore.privateappstore.domain.model.ManagementSummary;
import com.kosherstore.privateappstore.domain.model.RepositorySyncState;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.domain.model.SyncSource;
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository;
import com.kosherstore.privateappstore.util.PackageUtils;
import com.kosherstore.privateappstore.util.StoreStatsStore;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import retrofit2.HttpException;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B[\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0001\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010 J\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0096@\u00a2\u0006\u0002\u0010$J\u0018\u0010%\u001a\u0004\u0018\u00010#2\u0006\u0010\u001c\u001a\u00020\u001dH\u0082@\u00a2\u0006\u0002\u0010 J\u0016\u0010&\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010 J\u0014\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\"H\u0082@\u00a2\u0006\u0002\u0010$J\u0018\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010#0*2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0014\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\"0*H\u0016J\u0014\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\"0*H\u0016J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0*H\u0016J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00190*H\u0016J\u0016\u00100\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010 J\u0016\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u000203H\u0096@\u00a2\u0006\u0002\u00104J\u0016\u00105\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010 J\u001e\u00106\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00107\u001a\u000203H\u0096@\u00a2\u0006\u0002\u00108J\u000e\u00109\u001a\u00020\u001fH\u0096@\u00a2\u0006\u0002\u0010$J\u000e\u0010:\u001a\u00020\u001fH\u0096@\u00a2\u0006\u0002\u0010$J\u0010\u0010;\u001a\u00020\u001d2\u0006\u0010<\u001a\u00020=H\u0002J\u0016\u0010>\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010 J4\u0010?\u001a\u00020#*\u00020@2\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010A\u001a\u0004\u0018\u00010B2\b\b\u0002\u0010C\u001a\u0002032\b\b\u0002\u0010D\u001a\u000203H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006E"}, d2 = {"Lcom/kosherstore/privateappstore/data/repository/StoreAppRepositoryImpl;", "Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;", "context", "Landroid/content/Context;", "appDao", "Lcom/kosherstore/privateappstore/data/local/dao/AppDao;", "managedInstallDao", "Lcom/kosherstore/privateappstore/data/local/dao/ManagedInstallDao;", "appsApiService", "Lcom/kosherstore/privateappstore/data/remote/AppsApiService;", "gson", "Lcom/google/gson/Gson;", "downloadCoordinator", "Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;", "installCoordinator", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "packageChangeNotifier", "Lcom/kosherstore/privateappstore/data/install/PackageChangeNotifier;", "statsStore", "Lcom/kosherstore/privateappstore/util/StoreStatsStore;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Landroid/content/Context;Lcom/kosherstore/privateappstore/data/local/dao/AppDao;Lcom/kosherstore/privateappstore/data/local/dao/ManagedInstallDao;Lcom/kosherstore/privateappstore/data/remote/AppsApiService;Lcom/google/gson/Gson;Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;Lcom/kosherstore/privateappstore/data/install/PackageChangeNotifier;Lcom/kosherstore/privateappstore/util/StoreStatsStore;Lkotlinx/coroutines/CoroutineDispatcher;)V", "syncState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/kosherstore/privateappstore/domain/model/RepositorySyncState;", "buildOpenAppIntent", "Landroid/content/Intent;", "packageName", "", "cancelDownload", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkForUpdatesInBackground", "", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findApp", "installDownloadedApp", "loadSeedApps", "Lcom/kosherstore/privateappstore/data/remote/dto/StoreAppDto;", "observeApp", "Lkotlinx/coroutines/flow/Flow;", "observeApps", "observeManagedApps", "observeManagementSummary", "Lcom/kosherstore/privateappstore/domain/model/ManagementSummary;", "observeSyncState", "pauseDownload", "refreshApps", "forceRemote", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resumeDownload", "startDownload", "autoInstall", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sync", "syncInstalledStatuses", "toReadableMessage", "error", "", "uninstallApp", "toDomain", "Lcom/kosherstore/privateappstore/data/local/AppEntity;", "downloadState", "Lcom/kosherstore/privateappstore/domain/model/DownloadState;", "isInstalling", "isManaged", "app_debug"})
public final class StoreAppRepositoryImpl implements com.kosherstore.privateappstore.domain.repository.StoreAppRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.local.dao.AppDao appDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao managedInstallDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.remote.AppsApiService appsApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.download.DownloadCoordinator downloadCoordinator = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.install.InstallCoordinator installCoordinator = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.install.PackageChangeNotifier packageChangeNotifier = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.util.StoreStatsStore statsStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kosherstore.privateappstore.domain.model.RepositorySyncState> syncState = null;
    
    @javax.inject.Inject()
    public StoreAppRepositoryImpl(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.local.dao.AppDao appDao, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao managedInstallDao, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.remote.AppsApiService appsApiService, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.download.DownloadCoordinator downloadCoordinator, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.install.InstallCoordinator installCoordinator, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.install.PackageChangeNotifier packageChangeNotifier, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.util.StoreStatsStore statsStore, @com.kosherstore.privateappstore.di.IoDispatcher()
    @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp>> observeApps() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp>> observeManagedApps() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.kosherstore.privateappstore.domain.model.StoreApp> observeApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.kosherstore.privateappstore.domain.model.ManagementSummary> observeManagementSummary() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.kosherstore.privateappstore.domain.model.RepositorySyncState> observeSyncState() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object refreshApps(boolean forceRemote, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object syncInstalledStatuses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object sync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object startDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, boolean autoInstall, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object resumeDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object pauseDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object cancelDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object installDownloadedApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.content.Intent buildOpenAppIntent(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object uninstallApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object checkForUpdatesInBackground(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.kosherstore.privateappstore.domain.model.StoreApp>> $completion) {
        return null;
    }
    
    private final java.lang.Object findApp(java.lang.String packageName, kotlin.coroutines.Continuation<? super com.kosherstore.privateappstore.domain.model.StoreApp> $completion) {
        return null;
    }
    
    private final java.lang.Object loadSeedApps(kotlin.coroutines.Continuation<? super java.util.List<com.kosherstore.privateappstore.data.remote.dto.StoreAppDto>> $completion) {
        return null;
    }
    
    private final java.lang.String toReadableMessage(java.lang.Throwable error) {
        return null;
    }
    
    private final com.kosherstore.privateappstore.domain.model.StoreApp toDomain(com.kosherstore.privateappstore.data.local.AppEntity $this$toDomain, android.content.Context context, com.kosherstore.privateappstore.domain.model.DownloadState downloadState, boolean isInstalling, boolean isManaged) {
        return null;
    }
}