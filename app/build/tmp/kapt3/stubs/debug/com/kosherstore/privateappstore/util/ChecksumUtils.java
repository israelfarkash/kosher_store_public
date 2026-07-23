package com.kosherstore.privateappstore.util;

import com.kosherstore.privateappstore.domain.model.ChecksumType;
import java.io.File;
import java.security.MessageDigest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\f"}, d2 = {"Lcom/kosherstore/privateappstore/util/ChecksumUtils;", "", "()V", "computeChecksum", "", "file", "Ljava/io/File;", "type", "Lcom/kosherstore/privateappstore/domain/model/ChecksumType;", "isValid", "", "expectedChecksum", "app_debug"})
public final class ChecksumUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.util.ChecksumUtils INSTANCE = null;
    
    private ChecksumUtils() {
        super();
    }
    
    public final boolean isValid(@org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    java.lang.String expectedChecksum, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.ChecksumType type) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String computeChecksum(@org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.ChecksumType type) {
        return null;
    }
}