package com.kosherstore.privateappstore.data.install;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0010"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallStatusReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "installCoordinator", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "getInstallCoordinator", "()Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "setInstallCoordinator", "(Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "Companion", "app_debug"})
public final class InstallStatusReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject()
    public com.kosherstore.privateappstore.data.install.InstallCoordinator installCoordinator;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_INSTALL_COMMIT = "com.kosherstore.privateappstore.INSTALL_COMMIT";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_PACKAGE_NAME = "extra_package_name";
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.data.install.InstallStatusReceiver.Companion Companion = null;
    
    public InstallStatusReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.install.InstallCoordinator getInstallCoordinator() {
        return null;
    }
    
    public final void setInstallCoordinator(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.install.InstallCoordinator p0) {
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/InstallStatusReceiver$Companion;", "", "()V", "ACTION_INSTALL_COMMIT", "", "EXTRA_PACKAGE_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}