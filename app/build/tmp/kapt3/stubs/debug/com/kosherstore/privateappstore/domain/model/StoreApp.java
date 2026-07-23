package com.kosherstore.privateappstore.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b/\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\u0002\u0010\u0019J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u000fH\u00c6\u0003J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011H\u00c6\u0003J\u0010\u00107\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\'J\t\u00108\u001a\u00020\u0014H\u00c6\u0003J\t\u00109\u001a\u00020\u0016H\u00c6\u0003J\t\u0010:\u001a\u00020\u0018H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u0003H\u00c6\u0003J\u00b8\u0001\u0010C\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u00c6\u0001\u00a2\u0006\u0002\u0010DJ\u0013\u0010E\u001a\u00020\u00182\b\u0010F\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010G\u001a\u00020HH\u00d6\u0001J\t\u0010I\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010(\u001a\u0004\b&\u0010\'R\u0011\u0010)\u001a\u00020\u00188F\u00a2\u0006\u0006\u001a\u0004\b)\u0010*R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010*R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001bR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001bR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001b\u00a8\u0006J"}, d2 = {"Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "", "name", "", "packageName", "versionCode", "", "versionName", "apkUrl", "iconUrl", "description", "category", "size", "checksum", "checksumType", "Lcom/kosherstore/privateappstore/domain/model/ChecksumType;", "screenshots", "", "installedVersionCode", "installStatus", "Lcom/kosherstore/privateappstore/domain/model/InstallStatus;", "downloadState", "Lcom/kosherstore/privateappstore/domain/model/DownloadState;", "isManagedByStore", "", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/kosherstore/privateappstore/domain/model/ChecksumType;Ljava/util/List;Ljava/lang/Long;Lcom/kosherstore/privateappstore/domain/model/InstallStatus;Lcom/kosherstore/privateappstore/domain/model/DownloadState;Z)V", "getApkUrl", "()Ljava/lang/String;", "getCategory", "getChecksum", "getChecksumType", "()Lcom/kosherstore/privateappstore/domain/model/ChecksumType;", "getDescription", "getDownloadState", "()Lcom/kosherstore/privateappstore/domain/model/DownloadState;", "getIconUrl", "getInstallStatus", "()Lcom/kosherstore/privateappstore/domain/model/InstallStatus;", "getInstalledVersionCode", "()Ljava/lang/Long;", "Ljava/lang/Long;", "isInstalled", "()Z", "getName", "getPackageName", "getScreenshots", "()Ljava/util/List;", "getSize", "getVersionCode", "()J", "getVersionName", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/kosherstore/privateappstore/domain/model/ChecksumType;Ljava/util/List;Ljava/lang/Long;Lcom/kosherstore/privateappstore/domain/model/InstallStatus;Lcom/kosherstore/privateappstore/domain/model/DownloadState;Z)Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class StoreApp {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String packageName = null;
    private final long versionCode = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String versionName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String apkUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String iconUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String category = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String size = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String checksum = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.model.ChecksumType checksumType = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> screenshots = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long installedVersionCode = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.model.InstallStatus installStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.model.DownloadState downloadState = null;
    private final boolean isManagedByStore = false;
    
    public StoreApp(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName, long versionCode, @org.jetbrains.annotations.Nullable()
    java.lang.String versionName, @org.jetbrains.annotations.NotNull()
    java.lang.String apkUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String iconUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String size, @org.jetbrains.annotations.NotNull()
    java.lang.String checksum, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.ChecksumType checksumType, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> screenshots, @org.jetbrains.annotations.Nullable()
    java.lang.Long installedVersionCode, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.InstallStatus installStatus, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.DownloadState downloadState, boolean isManagedByStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPackageName() {
        return null;
    }
    
    public final long getVersionCode() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getVersionName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getApkUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIconUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSize() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChecksum() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.ChecksumType getChecksumType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getScreenshots() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getInstalledVersionCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.InstallStatus getInstallStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.DownloadState getDownloadState() {
        return null;
    }
    
    public final boolean isManagedByStore() {
        return false;
    }
    
    public final boolean isInstalled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.ChecksumType component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.InstallStatus component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.DownloadState component15() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.StoreApp copy(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName, long versionCode, @org.jetbrains.annotations.Nullable()
    java.lang.String versionName, @org.jetbrains.annotations.NotNull()
    java.lang.String apkUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String iconUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String size, @org.jetbrains.annotations.NotNull()
    java.lang.String checksum, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.ChecksumType checksumType, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> screenshots, @org.jetbrains.annotations.Nullable()
    java.lang.Long installedVersionCode, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.InstallStatus installStatus, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.DownloadState downloadState, boolean isManagedByStore) {
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