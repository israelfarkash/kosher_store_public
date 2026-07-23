package com.kosherstore.privateappstore.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.kosherstore.privateappstore.data.local.ManagedInstallEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class ManagedInstallDao_Impl implements ManagedInstallDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ManagedInstallEntity> __insertionAdapterOfManagedInstallEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public ManagedInstallDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfManagedInstallEntity = new EntityInsertionAdapter<ManagedInstallEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `managed_installs` (`packageName`,`appName`,`installedVersionCode`,`updatedAt`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ManagedInstallEntity entity) {
        if (entity.getPackageName() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getPackageName());
        }
        if (entity.getAppName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getAppName());
        }
        if (entity.getInstalledVersionCode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getInstalledVersionCode());
        }
        statement.bindLong(4, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM managed_installs WHERE packageName = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final ManagedInstallEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfManagedInstallEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final String packageName, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        if (packageName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, packageName);
        }
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
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ManagedInstallEntity>> observeManagedApps() {
    final String _sql = "SELECT * FROM managed_installs ORDER BY appName COLLATE NOCASE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"managed_installs"}, new Callable<List<ManagedInstallEntity>>() {
      @Override
      @NonNull
      public List<ManagedInstallEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfInstalledVersionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "installedVersionCode");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ManagedInstallEntity> _result = new ArrayList<ManagedInstallEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ManagedInstallEntity _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final Long _tmpInstalledVersionCode;
            if (_cursor.isNull(_cursorIndexOfInstalledVersionCode)) {
              _tmpInstalledVersionCode = null;
            } else {
              _tmpInstalledVersionCode = _cursor.getLong(_cursorIndexOfInstalledVersionCode);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ManagedInstallEntity(_tmpPackageName,_tmpAppName,_tmpInstalledVersionCode,_tmpUpdatedAt);
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
  public Object getAll(final Continuation<? super List<ManagedInstallEntity>> $completion) {
    final String _sql = "SELECT * FROM managed_installs";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ManagedInstallEntity>>() {
      @Override
      @NonNull
      public List<ManagedInstallEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfInstalledVersionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "installedVersionCode");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ManagedInstallEntity> _result = new ArrayList<ManagedInstallEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ManagedInstallEntity _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final Long _tmpInstalledVersionCode;
            if (_cursor.isNull(_cursorIndexOfInstalledVersionCode)) {
              _tmpInstalledVersionCode = null;
            } else {
              _tmpInstalledVersionCode = _cursor.getLong(_cursorIndexOfInstalledVersionCode);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ManagedInstallEntity(_tmpPackageName,_tmpAppName,_tmpInstalledVersionCode,_tmpUpdatedAt);
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
