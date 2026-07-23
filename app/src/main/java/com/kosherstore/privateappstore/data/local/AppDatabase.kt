package com.kosherstore.privateappstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kosherstore.privateappstore.data.local.dao.AppDao
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao

@Database(
    entities = [AppEntity::class, ManagedInstallEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
    abstract fun managedInstallDao(): ManagedInstallDao
}
