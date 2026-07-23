package com.kosherstore.privateappstore.data.install;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.kosherstore.privateappstore.data.download.DownloadCoordinator;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/PackageChangeReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "downloadCoordinator", "Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;", "getDownloadCoordinator", "()Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;", "setDownloadCoordinator", "(Lcom/kosherstore/privateappstore/data/download/DownloadCoordinator;)V", "installCoordinator", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "getInstallCoordinator", "()Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "setInstallCoordinator", "(Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class PackageChangeReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject()
    public com.kosherstore.privateappstore.data.install.InstallCoordinator installCoordinator;
    @javax.inject.Inject()
    public com.kosherstore.privateappstore.data.download.DownloadCoordinator downloadCoordinator;
    
    public PackageChangeReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.install.InstallCoordinator getInstallCoordinator() {
        return null;
    }
    
    public final void setInstallCoordinator(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.install.InstallCoordinator p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.download.DownloadCoordinator getDownloadCoordinator() {
        return null;
    }
    
    public final void setDownloadCoordinator(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.download.DownloadCoordinator p0) {
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
}