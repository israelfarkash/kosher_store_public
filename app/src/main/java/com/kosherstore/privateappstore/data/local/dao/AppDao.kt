package com.kosherstore.privateappstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kosherstore.privateappstore.data.local.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM apps ORDER BY name COLLATE NOCASE")
    fun observeApps(): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE packageName = :packageName LIMIT 1")
    fun observeApp(packageName: String): Flow<AppEntity?>

    @Query("SELECT * FROM apps ORDER BY name COLLATE NOCASE")
    suspend fun getAll(): List<AppEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<AppEntity>)

    @Query("DELETE FROM apps")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(items: List<AppEntity>) {
        clearAll()
        upsertAll(items)
    }
}
