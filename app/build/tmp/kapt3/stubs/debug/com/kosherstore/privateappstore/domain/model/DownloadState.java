package com.kosherstore.privateappstore.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001a\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\fH\u00c6\u0003JP\u0010 \u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001\u00a2\u0006\u0002\u0010!J\u0013\u0010\"\u001a\u00020\f2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001J\t\u0010%\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\n\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006&"}, d2 = {"Lcom/kosherstore/privateappstore/domain/model/DownloadState;", "", "requestId", "", "progress", "", "status", "Lcom/kosherstore/privateappstore/domain/model/DownloadStatus;", "localFilePath", "", "errorMessage", "autoInstall", "", "(Ljava/lang/Long;ILcom/kosherstore/privateappstore/domain/model/DownloadStatus;Ljava/lang/String;Ljava/lang/String;Z)V", "getAutoInstall", "()Z", "getErrorMessage", "()Ljava/lang/String;", "getLocalFilePath", "getProgress", "()I", "getRequestId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getStatus", "()Lcom/kosherstore/privateappstore/domain/model/DownloadStatus;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/Long;ILcom/kosherstore/privateappstore/domain/model/DownloadStatus;Ljava/lang/String;Ljava/lang/String;Z)Lcom/kosherstore/privateappstore/domain/model/DownloadState;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class DownloadState {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long requestId = null;
    private final int progress = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.model.DownloadStatus status = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String localFilePath = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    private final boolean autoInstall = false;
    
    public DownloadState(@org.jetbrains.annotations.Nullable()
    java.lang.Long requestId, int progress, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.DownloadStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String localFilePath, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, boolean autoInstall) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getRequestId() {
        return null;
    }
    
    public final int getProgress() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.DownloadStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLocalFilePath() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public final boolean getAutoInstall() {
        return false;
    }
    
    public DownloadState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.DownloadStatus component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.domain.model.DownloadState copy(@org.jetbrains.annotations.Nullable()
    java.lang.Long requestId, int progress, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.DownloadStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String localFilePath, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, boolean autoInstall) {
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