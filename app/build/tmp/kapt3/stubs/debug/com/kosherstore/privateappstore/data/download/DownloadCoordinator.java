package com.kosherstore.privateappstore.data.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import com.kosherstore.privateappstore.di.IoDispatcher;
import com.kosherstore.privateappstore.domain.model.DownloadState;
import com.kosherstore.privateappstore.domain.model.DownloadStatus;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.data.install.InstallCoordinator;
import com.kosherstore.privateappstore.util.ChecksumUtils;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.StateFlow;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0001CB+\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0001\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u0004\u0018\u00010 2\u0006\u0010\u001c\u001a\u00020\u000eJ\u0016\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020&H\u0086@\u00a2\u0006\u0002\u0010\'J\u0018\u0010(\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020\u000eH\u0002J\u0016\u0010*\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010+\u001a\u00020\u001bH\u0082@\u00a2\u0006\u0002\u0010,J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0002J\u0016\u00101\u001a\u00020\u001b2\u0006\u00102\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u00103J\u0010\u00104\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0002J\u0016\u00105\u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u00106J \u00107\u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u00108\u001a\u000209H\u0086@\u00a2\u0006\u0002\u0010:J\b\u0010;\u001a\u00020\u001bH\u0002J \u0010<\u001a\u00020\u001b2\u0006\u00102\u001a\u00020\u00122\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020.H\u0002J\u0018\u0010@\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010A\u001a\u00020\u000fH\u0002J\u0010\u0010B\u001a\u00020\u001b2\u0006\u00102\u001a\u00020\u0012H\u0002R \u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006D"}, d2 = {"Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;", "", "context", "Landroid/content/Context;", "downloadManager", "Landroid/app/DownloadManager;", "installCoordinator", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Landroid/content/Context;Landroid/app/DownloadManager;Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;Lkotlinx/coroutines/CoroutineDispatcher;)V", "_downloadStates", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "Lcom/kosherstore/privateappstore/domain/model/DownloadState;", "activeDownloads", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator$ActiveDownload;", "downloadStates", "Lkotlinx/coroutines/flow/StateFlow;", "getDownloadStates", "()Lkotlinx/coroutines/flow/StateFlow;", "pausedDownloads", "scope", "Lkotlinx/coroutines/CoroutineScope;", "cancelDownload", "", "packageName", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clear", "createTargetFile", "Ljava/io/File;", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "getDownloadedFile", "handleSystemDownloadBroadcast", "downloadId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markFailed", "message", "pauseDownload", "pollActiveDownloads", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readProgress", "", "cursor", "Landroid/database/Cursor;", "refreshDownloadState", "active", "(Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator$ActiveDownload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeState", "resumeDownload", "(Lcom/kosherstore/privateappstore/domain/model/StoreApp;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startDownload", "autoInstall", "", "(Lcom/kosherstore/privateappstore/domain/model/StoreApp;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startService", "updateRunningState", "status", "Lcom/kosherstore/privateappstore/domain/model/DownloadStatus;", "progress", "updateState", "state", "verifyFile", "ActiveDownload", "app_debug"})
public final class DownloadCoordinator {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.app.DownloadManager downloadManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.data.install.InstallCoordinator installCoordinator = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.kosherstore.privateappstore.data.download.DownloadCoordinator.ActiveDownload> activeDownloads = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.kosherstore.privateappstore.data.download.DownloadCoordinator.ActiveDownload> pausedDownloads = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.kosherstore.privateappstore.domain.model.DownloadState>> _downloadStates = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.kosherstore.privateappstore.domain.model.DownloadState>> downloadStates = null;
    
    @javax.inject.Inject()
    public DownloadCoordinator(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.app.DownloadManager downloadManager, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.install.InstallCoordinator installCoordinator, @com.kosherstore.privateappstore.di.IoDispatcher()
    @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.kosherstore.privateappstore.domain.model.DownloadState>> getDownloadStates() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object startDownload(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app, boolean autoInstall, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object resumeDownload(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pauseDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object cancelDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clear(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File getDownloadedFile(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object handleSystemDownloadBroadcast(long downloadId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object pollActiveDownloads(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object refreshDownloadState(com.kosherstore.privateappstore.data.download.DownloadCoordinator.ActiveDownload active, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void updateRunningState(com.kosherstore.privateappstore.data.download.DownloadCoordinator.ActiveDownload active, com.kosherstore.privateappstore.domain.model.DownloadStatus status, int progress) {
    }
    
    private final void verifyFile(com.kosherstore.privateappstore.data.download.DownloadCoordinator.ActiveDownload active) {
    }
    
    private final void markFailed(java.lang.String packageName, java.lang.String message) {
    }
    
    private final int readProgress(android.database.Cursor cursor) {
        return 0;
    }
    
    private final java.io.File createTargetFile(com.kosherstore.privateappstore.domain.model.StoreApp app) {
        return null;
    }
    
    private final void updateState(java.lang.String packageName, com.kosherstore.privateappstore.domain.model.DownloadState state) {
    }
    
    private final void removeState(java.lang.String packageName) {
    }
    
    private final void startService() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u001b\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\t\u0010!\u001a\u00020\tH\u00c6\u0003JE\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\tH\u00c6\u0001J\u0013\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\u000bH\u00d6\u0001J\t\u0010&\u001a\u00020\'H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\f\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006("}, d2 = {"Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator$ActiveDownload;", "", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "requestId", "", "targetFile", "Ljava/io/File;", "autoInstall", "", "progress", "", "isVerifying", "(Lcom/kosherstore/privateappstore/domain/model/StoreApp;JLjava/io/File;ZIZ)V", "getApp", "()Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "getAutoInstall", "()Z", "setVerifying", "(Z)V", "getProgress", "()I", "setProgress", "(I)V", "getRequestId", "()J", "getTargetFile", "()Ljava/io/File;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
    static final class ActiveDownload {
        @org.jetbrains.annotations.NotNull()
        private final com.kosherstore.privateappstore.domain.model.StoreApp app = null;
        private final long requestId = 0L;
        @org.jetbrains.annotations.NotNull()
        private final java.io.File targetFile = null;
        private final boolean autoInstall = false;
        private int progress;
        private boolean isVerifying;
        
        public ActiveDownload(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.StoreApp app, long requestId, @org.jetbrains.annotations.NotNull()
        java.io.File targetFile, boolean autoInstall, int progress, boolean isVerifying) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.domain.model.StoreApp getApp() {
            return null;
        }
        
        public final long getRequestId() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File getTargetFile() {
            return null;
        }
        
        public final boolean getAutoInstall() {
            return false;
        }
        
        public final int getProgress() {
            return 0;
        }
        
        public final void setProgress(int p0) {
        }
        
        public final boolean isVerifying() {
            return false;
        }
        
        public final void setVerifying(boolean p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.domain.model.StoreApp component1() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        public final int component5() {
            return 0;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kosherstore.privateappstore.data.download.DownloadCoordinator.ActiveDownload copy(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.StoreApp app, long requestId, @org.jetbrains.annotations.NotNull()
        java.io.File targetFile, boolean autoInstall, int progress, boolean isVerifying) {
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