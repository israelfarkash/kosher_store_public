package com.kosherstore.privateappstore.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.kosherstore.privateappstore.data.local.dao.AppDao;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/kosherstore/privateappstore/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "appDao", "Lcom/kosherstore/privateappstore/data/local/dao/AppDao;", "managedInstallDao", "Lcom/kosherstore/privateappstore/data/local/dao/ManagedInstallDao;", "app_debug"})
@androidx.room.Database(entities = {com.kosherstore.privateappstore.data.local.AppEntity.class, com.kosherstore.privateappstore.data.local.ManagedInstallEntity.class}, version = 2, exportSchema = true)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.kosherstore.privateappstore.data.local.dao.AppDao appDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao managedInstallDao();
}