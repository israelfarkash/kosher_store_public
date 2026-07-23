package com.kosherstore.privateappstore.data.download;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import com.kosherstore.privateappstore.util.NotificationHelper;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\fH\u0016J\b\u0010\u0012\u001a\u00020\fH\u0016J\"\u0010\u0013\u001a\u00020\u00142\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/kosherstore/privateappstore/data/download/DownloadService;", "Landroid/app/Service;", "()V", "downloadCoordinator", "Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;", "getDownloadCoordinator", "()Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;", "setDownloadCoordinator", "(Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;)V", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "observeDownloads", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "app_debug"})
public final class DownloadService extends android.app.Service {
    @javax.inject.Inject()
    public com.kosherstore.privateappstore.data.download.DownloadCoordinator downloadCoordinator;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    
    public DownloadService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.download.DownloadCoordinator getDownloadCoordinator() {
        return null;
    }
    
    public final void setDownloadCoordinator(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.download.DownloadCoordinator p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void observeDownloads() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
}