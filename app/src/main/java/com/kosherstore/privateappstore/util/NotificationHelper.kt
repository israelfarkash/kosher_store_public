package com.kosherstore.privateappstore.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.ui.MainActivity

object NotificationHelper {
    const val CHANNEL_UPDATES = "updates_channel"
    const val CHANNEL_DOWNLOADS = "downloads_channel"
    
    private const val UPDATES_NOTIFICATION_ID = 1001
    const val DOWNLOAD_SERVICE_NOTIFICATION_ID = 2001

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Updates Channel
        val updatesChannel = NotificationChannel(
            CHANNEL_UPDATES,
            context.getString(R.string.notification_channel_updates),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_updates_description)
        }
        
        // Downloads Channel
        val downloadsChannel = NotificationChannel(
            CHANNEL_DOWNLOADS,
            context.getString(R.string.notification_channel_downloads),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = context.getString(R.string.notification_channel_downloads_description)
            setShowBadge(false)
        }
        
        manager.createNotificationChannel(updatesChannel)
        manager.createNotificationChannel(downloadsChannel)
    }

    fun buildDownloadForegroundNotification(context: Context, contentText: String): Notification {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            91,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_DOWNLOADS)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_download_active))
            .setContentText(contentText)
            .setOngoing(true)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun updateNotification(context: Context, notificationId: Int, notification: Notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        try {
            NotificationManagerCompat.from(context).notify(notificationId, notification)
        } catch (e: SecurityException) {
            // Handle cases where permission might have been revoked just now
        }
    }

    fun showUpdatesNotification(context: Context, updatesCount: Int) {
        if (updatesCount <= 0) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            90,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_UPDATES)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_updates_title))
            .setContentText(context.getString(R.string.notification_updates_body, updatesCount))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(UPDATES_NOTIFICATION_ID, notification)
        } catch (e: SecurityException) {
            // Handle permission issues
        }
    }
}
