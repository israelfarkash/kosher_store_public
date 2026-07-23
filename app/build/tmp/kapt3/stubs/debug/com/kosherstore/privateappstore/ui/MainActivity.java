package com.kosherstore.privateappstore.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.kosherstore.privateappstore.R;
import com.kosherstore.privateappstore.data.install.InstallCoordinator;
import com.kosherstore.privateappstore.databinding.ActivityMainBinding;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u000fH\u0002J\u0012\u0010\u0013\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u000fH\u0002J\b\u0010\u0019\u001a\u00020\u000fH\u0002J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/kosherstore/privateappstore/ui/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/kosherstore/privateappstore/databinding/ActivityMainBinding;", "installCoordinator", "Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "getInstallCoordinator", "()Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;", "setInstallCoordinator", "(Lcom/kosherstore/privateappstore/data/install/InstallCoordinator;)V", "notificationPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "attachBaseContext", "", "newBase", "Landroid/content/Context;", "observeInstallEvents", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "requestIgnoreBatteryOptimizationsIfNeeded", "requestNotificationPermissionIfNeeded", "showInstallSuccessDialog", "appName", "packageName", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    @javax.inject.Inject()
    public com.kosherstore.privateappstore.data.install.InstallCoordinator installCoordinator;
    private com.kosherstore.privateappstore.databinding.ActivityMainBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> notificationPermissionLauncher = null;
    
    public MainActivity() {
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
    protected void attachBaseContext(@org.jetbrains.annotations.NotNull()
    android.content.Context newBase) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    private final void observeInstallEvents() {
    }
    
    private final void showInstallSuccessDialog(java.lang.String appName, java.lang.String packageName) {
    }
    
    private final void requestNotificationPermissionIfNeeded() {
    }
    
    private final void requestIgnoreBatteryOptimizationsIfNeeded() {
    }
}