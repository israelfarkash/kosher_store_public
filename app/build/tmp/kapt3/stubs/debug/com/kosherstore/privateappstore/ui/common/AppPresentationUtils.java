package com.kosherstore.privateappstore.ui.common;

import android.content.Context;
import com.kosherstore.privateappstore.R;
import com.kosherstore.privateappstore.domain.model.InstallStatus;
import com.kosherstore.privateappstore.domain.model.StoreApp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u001d\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/kosherstore/privateappstore/ui/common/AppPresentationUtils;", "", "()V", "isPrimaryEnabled", "", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "primaryActionLabelRes", "", "secondaryActionLabelRes", "mode", "Lcom/kosherstore/privateappstore/ui/common/AppCardMode;", "(Lcom/kosherstore/privateappstore/domain/model/StoreApp;Lcom/kosherstore/privateappstore/ui/common/AppCardMode;)Ljava/lang/Integer;", "shouldShowProgress", "statusColor", "context", "Landroid/content/Context;", "statusText", "", "app_debug"})
public final class AppPresentationUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.ui.common.AppPresentationUtils INSTANCE = null;
    
    private AppPresentationUtils() {
        super();
    }
    
    public final int primaryActionLabelRes(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
        return 0;
    }
    
    public final boolean isPrimaryEnabled(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer secondaryActionLabelRes(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.ui.common.AppCardMode mode) {
        return null;
    }
    
    public final boolean shouldShowProgress(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
        return false;
    }
    
    public final int statusColor(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String statusText(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.model.StoreApp app) {
        return null;
    }
}