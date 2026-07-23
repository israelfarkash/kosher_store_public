package com.kosherstore.privateappstore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
    val screenshotsJson: String = "[]",
    val lastSyncedAt: Long
) {
    fun getScreenshots(): List<String> {
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(screenshotsJson, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
