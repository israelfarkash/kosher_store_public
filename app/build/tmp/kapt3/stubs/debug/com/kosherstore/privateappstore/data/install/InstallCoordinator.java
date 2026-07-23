package com.kosherstore.privateappstore.data.install;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import com.kosherstore.privateappstore.data.local.ManagedInstallEntity;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;
import com.kosherstore.privateappstore.di.IoDispatcher;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.util.PackageUtils;
import com.kosherstore.privateappstore.util.StoreStatsStore;
import com.kosherstore.privateappstore.util.XapkUtils;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001:\u0002>?B3\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0001\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u001e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u0013H\u0082@\u00a2\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020\"2\u0006\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u001e\u0010*\u001a\u00020\"2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0086@\u00a2\u0006\u0002\u0010/J\u001e\u00100\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00132\u0006\u0010-\u001a\u00020.H\u0082@\u00a2\u0006\u0002\u00101J\u0018\u00102\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00132\u0006\u00103\u001a\u000204H\u0002J\u0016\u00105\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u00106J\u001e\u00107\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00132\u0006\u00108\u001a\u000204H\u0086@\u00a2\u0006\u0002\u00109J\u0010\u0010:\u001a\u0002042\u0006\u0010-\u001a\u00020.H\u0002J \u0010;\u001a\u0002042\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010<\u001a\u000204H\u0002J\u0016\u0010=\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u00106R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020 0\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006@"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "", "context", "Landroid/content/Context;", "managedInstallDao", "Lcom/kosherstore/privateappstore/data/local/dao/ManagedInstallDao;", "packageChangeNotifier", "Lcom/kosherstore/privateappstore/data/install/PackageChangeNotifier;", "statsStore", "Lcom/kosherstore/privateappstore/util/StoreStatsStore;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Landroid/content/Context;Lcom/kosherstore/privateappstore/data/local/dao/ManagedInstallDao;Lcom/kosherstore/privateappstore/data/install/PackageChangeNotifier;Lcom/kosherstore/privateappstore/util/StoreStatsStore;Lkotlinx/coroutines/CoroutineDispatcher;)V", "_events", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent;", "_installingPackages", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "events", "Lkotlinx/coroutines/flow/SharedFlow;", "getEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "installingPackages", "Lkotlinx/coroutines/flow/StateFlow;", "getInstallingPackages", "()Lkotlinx/coroutines/flow/StateFlow;", "pendingInstalls", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$PendingInstall;", "pendingUninstalls", "", "emitFailure", "", "packageName", "message", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handlePackageInstallerCallback", "intent", "Landroid/content/Intent;", "(Landroid/content/Intent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "installDownloadedApk", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "apkFile", "Ljava/io/File;", "(Lcom/kosherstore/privateappstore/domain/model/StoreApp;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "launchStandardInstaller", "(Ljava/lang/String;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markInstalling", "active", "", "onPackageChanged", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onPackageRemoved", "replacing", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryRootInstall", "trySessionInstall", "isDeviceOwner", "uninstallApp", "InstallEvent", "PendingInstall", "app_debug"})
public final class InstallCoordinator {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao managedInstallDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.install.PackageChangeNotifier packageChangeNotifier = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.util.StoreStatsStore statsStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.kosherstore.privateappstore.data.install.InstallCoordinator.PendingInstall> pendingInstalls = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Long> pendingUninstalls = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent> _events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _installingPackages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent> events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> installingPackages = null;
    
    @javax.inject.Inject()
    public InstallCoordinator(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao managedInstallDao, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.install.PackageChangeNotifier packageChangeNotifier, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.util.StoreStatsStore statsStore, @com.kosherstore.privateappstore.di.IoDispatcher()
    @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent> getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getInstallingPackages() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object installDownloadedApk(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app, @org.jetbrains.annotations.NotNull()
    java.io.File apkFile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uninstallApp(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object handlePackageInstallerCallback(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object onPackageChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object onPackageRemoved(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, boolean replacing, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object launchStandardInstaller(java.lang.String packageName, java.io.File apkFile, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final boolean tryRootInstall(java.io.File apkFile) {
        return false;
    }
    
    private final boolean trySessionInstall(com.kosherstore.privateappstore.domain.model.StoreApp app, java.io.File apkFile, boolean isDeviceOwner) {
        return false;
    }
    
    private final java.lang.Object emitFailure(java.lang.String packageName, java.lang.String message, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void markInstalling(java.lang.String packageName, boolean active) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005\u0082\u0001\u0004\u0006\u0007\b\t\u00a8\u0006\n"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent;", "", "Failure", "Info", "LaunchSystemInstaller", "Success", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$Failure;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$Info;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$LaunchSystemInstaller;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$Success;", "app_debug"})
    public static abstract interface InstallEvent {
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$Failure;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent;", "packageName", "", "message", "(Ljava/lang/String;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getPackageName", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Failure implements com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String packageName = null;
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String message = null;
            
            public Failure(@org.jetbrains.annotations.NotNull()
            java.lang.String packageName, @org.jetbrains.annotations.NotNull()
            java.lang.String message) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getPackageName() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent.Failure copy(@org.jetbrains.annotations.NotNull()
            java.lang.String packageName, @org.jetbrains.annotations.NotNull()
            java.lang.String message) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$Info;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Info implements com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String message = null;
            
            public Info(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent.Info copy(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$LaunchSystemInstaller;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent;", "intent", "Landroid/content/Intent;", "(Landroid/content/Intent;)V", "getIntent", "()Landroid/content/Intent;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class LaunchSystemInstaller implements com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent {
            @org.jetbrains.annotations.NotNull()
            private final android.content.Intent intent = null;
            
            public LaunchSystemInstaller(@org.jetbrains.annotations.NotNull()
            android.content.Intent intent) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final android.content.Intent getIntent() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final android.content.Intent component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent.LaunchSystemInstaller copy(@org.jetbrains.annotations.NotNull()
            android.content.Intent intent) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent$Success;", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$InstallEvent;", "appName", "", "packageName", "(Ljava/lang/String;Ljava/lang/String;)V", "getAppName", "()Ljava/lang/String;", "getPackageName", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Success implements com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String appName = null;
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String packageName = null;
            
            public Success(@org.jetbrains.annotations.NotNull()
            java.lang.String appName, @org.jetbrains.annotations.NotNull()
            java.lang.String packageName) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getAppName() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getPackageName() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.kosherstore.privateappstore.data.install.InstallCoordinator.InstallEvent.Success copy(@org.jetbrains.annotations.NotNull()
            java.lang.String appName, @org.jetbrains.annotations.NotNull()
            java.lang.String packageName) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallCoordinator$PendingInstall;", "", "appName", "", "versionCode", "", "(Ljava/lang/String;J)V", "getAppName", "()Ljava/lang/String;", "getVersionCode", "()J", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    static final class PendingInstall {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String appName = null;
        private final long versionCode = 0L;
        
        public PendingInstall(@org.jetbrains.annotations.NotNull()
        java.lang.String appName, long versionCode) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAppName() {
            return null;
        }
        
        public final long getVersionCode() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.data.install.InstallCoordinator.PendingInstall copy(@org.jetbrains.annotations.NotNull()
        java.lang.String appName, long versionCode) {
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