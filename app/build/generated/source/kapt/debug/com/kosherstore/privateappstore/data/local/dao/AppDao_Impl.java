package com.kosherstore.privateappstore.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.kosherstore.privateappstore.data.local.AppEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AppEntity> __insertionAdapterOfAppEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public AppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppEntity = new EntityInsertionAdapter<AppEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `apps` (`packageName`,`name`,`versionCode`,`versionName`,`apkUrl`,`iconUrl`,`description`,`category`,`size`,`checksum`,`checksumType`,`screenshotsJson`,`lastSyncedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AppEntity entity) {
        if (entity.getPackageName() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getPackageName());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getVersionCode());
        if (entity.getVersionName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getVersionName());
        }
        if (entity.getApkUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getApkUrl());
        }
        if (entity.getIconUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getIconUrl());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCategory());
        }
        if (entity.getSize() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSize());
        }
        if (entity.getChecksum() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getChecksum());
        }
        if (entity.getChecksumType() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getChecksumType());
        }
        if (entity.getScreenshotsJson() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getScreenshotsJson());
        }
        statement.bindLong(13, entity.getLastSyncedAt());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM apps";
        return _query;
      }
    };
  }

  @Override
  public Object upsertAll(final List<AppEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAppEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object replaceAll(final List<AppEntity> items,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> AppDao.DefaultImpls.replaceAll(AppDao_Impl.this, items, __cont), $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AppEntity>> observeApps() {
    final String _sql = "SELECT * FROM apps ORDER BY name COLLATE NOCASE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"apps"}, new Callable<List<AppEntity>>() {
      @Override
      @NonNull
      public List<AppEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfVersionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "versionCode");
          final int _cursorIndexOfVersionName = CursorUtil.getColumnIndexOrThrow(_cursor, "versionName");
          final int _cursorIndexOfApkUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "apkUrl");
          final int _cursorIndexOfIconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "iconUrl");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfChecksum = CursorUtil.getColumnIndexOrThrow(_cursor, "checksum");
          final int _cursorIndexOfChecksumType = CursorUtil.getColumnIndexOrThrow(_cursor, "checksumType");
          final int _cursorIndexOfScreenshotsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "screenshotsJson");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncedAt");
          final List<AppEntity> _result = new ArrayList<AppEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppEntity _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpVersionCode;
            _tmpVersionCode = _cursor.getLong(_cursorIndexOfVersionCode);
            final String _tmpVersionName;
            if (_cursor.isNull(_cursorIndexOfVersionName)) {
              _tmpVersionName = null;
            } else {
              _tmpVersionName = _cursor.getString(_cursorIndexOfVersionName);
            }
            final String _tmpApkUrl;
            if (_cursor.isNull(_cursorIndexOfApkUrl)) {
              _tmpApkUrl = null;
            } else {
              _tmpApkUrl = _cursor.getString(_cursorIndexOfApkUrl);
            }
            final String _tmpIconUrl;
            if (_cursor.isNull(_cursorIndexOfIconUrl)) {
              _tmpIconUrl = null;
            } else {
              _tmpIconUrl = _cursor.getString(_cursorIndexOfIconUrl);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSize;
            if (_cursor.isNull(_cursorIndexOfSize)) {
              _tmpSize = null;
            } else {
              _tmpSize = _cursor.getString(_cursorIndexOfSize);
            }
            final String _tmpChecksum;
            if (_cursor.isNull(_cursorIndexOfChecksum)) {
              _tmpChecksum = null;
            } else {
              _tmpChecksum = _cursor.getString(_cursorIndexOfChecksum);
            }
            final String _tmpChecksumType;
            if (_cursor.isNull(_cursorIndexOfChecksumType)) {
              _tmpChecksumType = null;
            } else {
              _tmpChecksumType = _cursor.getString(_cursorIndexOfChecksumType);
            }
            final String _tmpScreenshotsJson;
            if (_cursor.isNull(_cursorIndexOfScreenshotsJson)) {
              _tmpScreenshotsJson = null;
            } else {
              _tmpScreenshotsJson = _cursor.getString(_cursorIndexOfScreenshotsJson);
            }
            final long _tmpLastSyncedAt;
            _tmpLastSyncedAt = _cursor.getLong(_cursorIndexOfLastSyncedAt);
            _item = new AppEntity(_tmpPackageName,_tmpName,_tmpVersionCode,_tmpVersionName,_tmpApkUrl,_tmpIconUrl,_tmpDescription,_tmpCategory,_tmpSize,_tmpChecksum,_tmpChecksumType,_tmpScreenshotsJson,_tmpLastSyncedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<AppEntity> observeApp(final String packageName) {
    final String _sql = "SELECT * FROM apps WHERE packageName = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (packageName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, packageName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"apps"}, new Callable<AppEntity>() {
      @Override
      @Nullable
      public AppEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfVersionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "versionCode");
          final int _cursorIndexOfVersionName = CursorUtil.getColumnIndexOrThrow(_cursor, "versionName");
          final int _cursorIndexOfApkUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "apkUrl");
          final int _cursorIndexOfIconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "iconUrl");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfChecksum = CursorUtil.getColumnIndexOrThrow(_cursor, "checksum");
          final int _cursorIndexOfChecksumType = CursorUtil.getColumnIndexOrThrow(_cursor, "checksumType");
          final int _cursorIndexOfScreenshotsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "screenshotsJson");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncedAt");
          final AppEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpVersionCode;
            _tmpVersionCode = _cursor.getLong(_cursorIndexOfVersionCode);
            final String _tmpVersionName;
            if (_cursor.isNull(_cursorIndexOfVersionName)) {
              _tmpVersionName = null;
            } else {
              _tmpVersionName = _cursor.getString(_cursorIndexOfVersionName);
            }
            final String _tmpApkUrl;
            if (_cursor.isNull(_cursorIndexOfApkUrl)) {
              _tmpApkUrl = null;
            } else {
              _tmpApkUrl = _cursor.getString(_cursorIndexOfApkUrl);
            }
            final String _tmpIconUrl;
            if (_cursor.isNull(_cursorIndexOfIconUrl)) {
              _tmpIconUrl = null;
            } else {
              _tmpIconUrl = _cursor.getString(_cursorIndexOfIconUrl);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSize;
            if (_cursor.isNull(_cursorIndexOfSize)) {
              _tmpSize = null;
            } else {
              _tmpSize = _cursor.getString(_cursorIndexOfSize);
            }
            final String _tmpChecksum;
            if (_cursor.isNull(_cursorIndexOfChecksum)) {
              _tmpChecksum = null;
            } else {
              _tmpChecksum = _cursor.getString(_cursorIndexOfChecksum);
            }
            final String _tmpChecksumType;
            if (_cursor.isNull(_cursorIndexOfChecksumType)) {
              _tmpChecksumType = null;
            } else {
              _tmpChecksumType = _cursor.getString(_cursorIndexOfChecksumType);
            }
            final String _tmpScreenshotsJson;
            if (_cursor.isNull(_cursorIndexOfScreenshotsJson)) {
              _tmpScreenshotsJson = null;
            } else {
              _tmpScreenshotsJson = _cursor.getString(_cursorIndexOfScreenshotsJson);
            }
            final long _tmpLastSyncedAt;
            _tmpLastSyncedAt = _cursor.getLong(_cursorIndexOfLastSyncedAt);
            _result = new AppEntity(_tmpPackageName,_tmpName,_tmpVersionCode,_tmpVersionName,_tmpApkUrl,_tmpIconUrl,_tmpDescription,_tmpCategory,_tmpSize,_tmpChecksum,_tmpChecksumType,_tmpScreenshotsJson,_tmpLastSyncedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAll(final Continuation<? super List<AppEntity>> $completion) {
    final String _sql = "SELECT * FROM apps ORDER BY name COLLATE NOCASE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AppEntity>>() {
      @Override
      @NonNull
      public List<AppEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfVersionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "versionCode");
          final int _cursorIndexOfVersionName = CursorUtil.getColumnIndexOrThrow(_cursor, "versionName");
          final int _cursorIndexOfApkUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "apkUrl");
          final int _cursorIndexOfIconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "iconUrl");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfChecksum = CursorUtil.getColumnIndexOrThrow(_cursor, "checksum");
          final int _cursorIndexOfChecksumType = CursorUtil.getColumnIndexOrThrow(_cursor, "checksumType");
          final int _cursorIndexOfScreenshotsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "screenshotsJson");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncedAt");
          final List<AppEntity> _result = new ArrayList<AppEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppEntity _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpVersionCode;
            _tmpVersionCode = _cursor.getLong(_cursorIndexOfVersionCode);
            final String _tmpVersionName;
            if (_cursor.isNull(_cursorIndexOfVersionName)) {
              _tmpVersionName = null;
            } else {
              _tmpVersionName = _cursor.getString(_cursorIndexOfVersionName);
            }
            final String _tmpApkUrl;
            if (_cursor.isNull(_cursorIndexOfApkUrl)) {
              _tmpApkUrl = null;
            } else {
              _tmpApkUrl = _cursor.getString(_cursorIndexOfApkUrl);
            }
            final String _tmpIconUrl;
            if (_cursor.isNull(_cursorIndexOfIconUrl)) {
              _tmpIconUrl = null;
            } else {
              _tmpIconUrl = _cursor.getString(_cursorIndexOfIconUrl);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSize;
            if (_cursor.isNull(_cursorIndexOfSize)) {
              _tmpSize = null;
            } else {
              _tmpSize = _cursor.getString(_cursorIndexOfSize);
            }
            final String _tmpChecksum;
            if (_cursor.isNull(_cursorIndexOfChecksum)) {
              _tmpChecksum = null;
            } else {
              _tmpChecksum = _cursor.getString(_cursorIndexOfChecksum);
            }
            final String _tmpChecksumType;
            if (_cursor.isNull(_cursorIndexOfChecksumType)) {
              _tmpChecksumType = null;
            } else {
              _tmpChecksumType = _cursor.getString(_cursorIndexOfChecksumType);
            }
            final String _tmpScreenshotsJson;
            if (_cursor.isNull(_cursorIndexOfScreenshotsJson)) {
              _tmpScreenshotsJson = null;
            } else {
              _tmpScreenshotsJson = _cursor.getString(_cursorIndexOfScreenshotsJson);
            }
            final long _tmpLastSyncedAt;
            _tmpLastSyncedAt = _cursor.getLong(_cursorIndexOfLastSyncedAt);
            _item = new AppEntity(_tmpPackageName,_tmpName,_tmpVersionCode,_tmpVersionName,_tmpApkUrl,_tmpIconUrl,_tmpDescription,_tmpCategory,_tmpSize,_tmpChecksum,_tmpChecksumType,_tmpScreenshotsJson,_tmpLastSyncedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
