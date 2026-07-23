package com.kosherstore.privateappstore.util;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import androidx.core.content.pm.PackageInfoCompat;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006J\u001d\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006J\u0014\u0010\u0013\u001a\u00020\u0014*\u00020\u00152\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00a8\u0006\u0016"}, d2 = {"Lcom/kosherstore/privateappstore/util/PackageUtils;", "", "()V", "buildInstallerIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "apkFile", "Ljava/io/File;", "canRequestPackageInstalls", "", "getInstalledVersionCode", "", "packageName", "", "(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/Long;", "getLaunchIntent", "getPackageCodeSize", "isDeviceOwner", "getPackageInfoCompat", "Landroid/content/pm/PackageInfo;", "Landroid/content/pm/PackageManager;", "app_debug"})
public final class PackageUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.util.PackageUtils INSTANCE = null;
    
    private PackageUtils() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getInstalledVersionCode(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.content.Intent getLaunchIntent(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    public final long getPackageCodeSize(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return 0L;
    }
    
    public final boolean isDeviceOwner(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean canRequestPackageInstalls(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent buildInstallerIntent(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File apkFile) {
        return null;
    }
    
    @kotlin.Suppress(names = {"DEPRECATION"})
    private final android.content.pm.PackageInfo getPackageInfoCompat(android.content.pm.PackageManager $this$getPackageInfoCompat, java.lang.String packageName) {
        return null;
    }
}