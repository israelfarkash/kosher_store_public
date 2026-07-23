package com.kosherstore.privateappstore.util;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import com.kosherstore.privateappstore.R;
import com.kosherstore.privateappstore.ui.MainActivity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0007J\u001e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/kosherstore/privateappstore/util/NotificationHelper;", "", "()V", "CHANNEL_DOWNLOADS", "", "CHANNEL_UPDATES", "DOWNLOAD_SERVICE_NOTIFICATION_ID", "", "UPDATES_NOTIFICATION_ID", "buildDownloadForegroundNotification", "Landroid/app/Notification;", "context", "Landroid/content/Context;", "contentText", "createNotificationChannels", "", "showUpdatesNotification", "updatesCount", "updateNotification", "notificationId", "notification", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_UPDATES = "updates_channel";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_DOWNLOADS = "downloads_channel";
    private static final int UPDATES_NOTIFICATION_ID = 1001;
    public static final int DOWNLOAD_SERVICE_NOTIFICATION_ID = 2001;
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.util.NotificationHelper INSTANCE = null;
    
    private NotificationHelper() {
        super();
    }
    
    public final void createNotificationChannels(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Notification buildDownloadForegroundNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String contentText) {
        return null;
    }
    
    public final void updateNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int notificationId, @org.jetbrains.annotations.NotNull()
    android.app.Notification notification) {
    }
    
    public final void showUpdatesNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int updatesCount) {
    }
}