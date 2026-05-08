package com.kosherstore.privateappstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kosherstore.privateappstore.data.local.ManagedInstallEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ManagedInstallDao {
    @Query("SELECT * FROM managed_installs ORDER BY appName COLLATE NOCASE")
    fun observeManagedApps(): Flow<List<ManagedInstallEntity>>

    @Query("SELECT * FROM managed_installs")
    suspend fun getAll(): List<ManagedInstallEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ManagedInstallEntity)

    @Query("DELETE FROM managed_installs WHERE packageName = :packageName")
    suspend fun delete(packageName: String)
}
