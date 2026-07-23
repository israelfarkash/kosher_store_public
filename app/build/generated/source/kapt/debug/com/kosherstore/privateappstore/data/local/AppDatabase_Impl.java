package com.kosherstore.privateappstore.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.kosherstore.privateappstore.data.local.dao.AppDao;
import com.kosherstore.privateappstore.data.local.dao.AppDao_Impl;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AppDao _appDao;

  private volatile ManagedInstallDao _managedInstallDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `apps` (`packageName` TEXT NOT NULL, `name` TEXT NOT NULL, `versionCode` INTEGER NOT NULL, `versionName` TEXT, `apkUrl` TEXT NOT NULL, `iconUrl` TEXT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `size` TEXT NOT NULL, `checksum` TEXT NOT NULL, `checksumType` TEXT NOT NULL, `screenshotsJson` TEXT NOT NULL, `lastSyncedAt` INTEGER NOT NULL, PRIMARY KEY(`packageName`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `managed_installs` (`packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `installedVersionCode` INTEGER, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`packageName`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2ddf1926d7636b61fd3edfc0783681d7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `apps`");
        db.execSQL("DROP TABLE IF EXISTS `managed_installs`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsApps = new HashMap<String, TableInfo.Column>(13);
        _columnsApps.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("versionCode", new TableInfo.Column("versionCode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("versionName", new TableInfo.Column("versionName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("apkUrl", new TableInfo.Column("apkUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("iconUrl", new TableInfo.Column("iconUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("size", new TableInfo.Column("size", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("checksum", new TableInfo.Column("checksum", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("checksumType", new TableInfo.Column("checksumType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("screenshotsJson", new TableInfo.Column("screenshotsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsApps.put("lastSyncedAt", new TableInfo.Column("lastSyncedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysApps = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesApps = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoApps = new TableInfo("apps", _columnsApps, _foreignKeysApps, _indicesApps);
        final TableInfo _existingApps = TableInfo.read(db, "apps");
        if (!_infoApps.equals(_existingApps)) {
          return new RoomOpenHelper.ValidationResult(false, "apps(com.kosherstore.privateappstore.data.local.AppEntity).\n"
                  + " Expected:\n" + _infoApps + "\n"
                  + " Found:\n" + _existingApps);
        }
        final HashMap<String, TableInfo.Column> _columnsManagedInstalls = new HashMap<String, TableInfo.Column>(4);
        _columnsManagedInstalls.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsManagedInstalls.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsManagedInstalls.put("installedVersionCode", new TableInfo.Column("installedVersionCode", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsManagedInstalls.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysManagedInstalls = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesManagedInstalls = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoManagedInstalls = new TableInfo("managed_installs", _columnsManagedInstalls, _foreignKeysManagedInstalls, _indicesManagedInstalls);
        final TableInfo _existingManagedInstalls = TableInfo.read(db, "managed_installs");
        if (!_infoManagedInstalls.equals(_existingManagedInstalls)) {
          return new RoomOpenHelper.ValidationResult(false, "managed_installs(com.kosherstore.privateappstore.data.local.ManagedInstallEntity).\n"
                  + " Expected:\n" + _infoManagedInstalls + "\n"
                  + " Found:\n" + _existingManagedInstalls);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "2ddf1926d7636b61fd3edfc0783681d7", "7c0684200949851e1812b51806a2945a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "apps","managed_installs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `apps`");
      _db.execSQL("DELETE FROM `managed_installs`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AppDao.class, AppDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ManagedInstallDao.class, ManagedInstallDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AppDao appDao() {
    if (_appDao != null) {
      return _appDao;
    } else {
      synchronized(this) {
        if(_appDao == null) {
          _appDao = new AppDao_Impl(this);
        }
        return _appDao;
      }
    }
  }

  @Override
  public ManagedInstallDao managedInstallDao() {
    if (_managedInstallDao != null) {
      return _managedInstallDao;
    } else {
      synchronized(this) {
        if(_managedInstallDao == null) {
          _managedInstallDao = new ManagedInstallDao_Impl(this);
        }
        return _managedInstallDao;
      }
    }
  }
}
