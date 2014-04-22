package com.example.sqliteinjection.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * My own database open helper.
 */
public class MyDbOpenHelper extends SQLiteOpenHelper {
    private final Context context;
    private final String dbSrc;
    private final File dbPath;

    public MyDbOpenHelper(Context context, String dbSrc, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        this.context = context;
        this.dbSrc = dbSrc;
        this.dbPath = context.getDatabasePath(dbName);
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (!doesDbExist()) {
            copyDatabase();
        }
        return super.getReadableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (!doesDbExist()) {
            copyDatabase();
        }
        return super.getWritableDatabase();
    }

    private boolean doesDbExist() {
        try {
            SQLiteDatabase check = SQLiteDatabase.openDatabase(
                    dbPath.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            check.close();
            return true;
        } catch (SQLiteException ex) {
            return false;
        }
    }

    private void copyDatabase() {
        Util.extractAsset(context, dbSrc, dbPath);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Nothing to do
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nothing to do
    }
}
