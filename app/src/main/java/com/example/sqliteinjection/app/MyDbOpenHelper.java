package com.example.sqliteinjection.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

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
        try {
            File databaseDir = dbPath.getParentFile();
            if (!databaseDir.exists()) {
                databaseDir.mkdir();
            }

            InputStream inputStream = context.getAssets().open(dbSrc);
            int size = inputStream.available();
            ReadableByteChannel input = Channels.newChannel(inputStream);
            FileChannel output = new FileOutputStream(this.dbPath).getChannel();
            output.transferFrom(input, 0, size);

            output.close();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
