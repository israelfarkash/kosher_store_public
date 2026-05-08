package com.kosherstore.privateappstore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "managed_installs")
data class ManagedInstallEntity(
    @PrimaryKey val packageName: String,
    val appName: String,
    val installedVersionCode: Long?,
    val updatedAt: Long
)
