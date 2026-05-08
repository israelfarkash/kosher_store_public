package com.kosherstore.privateappstore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class AppEntity(
    @PrimaryKey val packageName: String,
    val name: String,
    val versionCode: Long,
    val versionName: String?,
    val apkUrl: String,
    val iconUrl: String,
    val description: String,
    val category: String,
    val size: String,
    val checksum: String,
    val checksumType: String,
    val lastSyncedAt: Long
)
