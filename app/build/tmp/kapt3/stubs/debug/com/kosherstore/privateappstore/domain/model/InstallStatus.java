package com.kosherstore.privateappstore.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2 = {"Lcom/kosherstore/privateappstore/domain/model/InstallStatus;", "", "(Ljava/lang/String;I)V", "NOT_INSTALLED", "DOWNLOADING", "PAUSED", "VERIFYING", "DOWNLOADED", "INSTALLING", "INSTALLED", "UPDATE_AVAILABLE", "FAILED", "app_debug"})
public enum InstallStatus {
    /*public static final*/ NOT_INSTALLED /* = new NOT_INSTALLED() */,
    /*public static final*/ DOWNLOADING /* = new DOWNLOADING() */,
    /*public static final*/ PAUSED /* = new PAUSED() */,
    /*public static final*/ VERIFYING /* = new VERIFYING() */,
    /*public static final*/ DOWNLOADED /* = new DOWNLOADED() */,
    /*public static final*/ INSTALLING /* = new INSTALLING() */,
    /*public static final*/ INSTALLED /* = new INSTALLED() */,
    /*public static final*/ UPDATE_AVAILABLE /* = new UPDATE_AVAILABLE() */,
    /*public static final*/ FAILED /* = new FAILED() */;
    
    InstallStatus() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.kosherstore.privateappstore.domain.model.InstallStatus> getEntries() {
        return null;
    }
}