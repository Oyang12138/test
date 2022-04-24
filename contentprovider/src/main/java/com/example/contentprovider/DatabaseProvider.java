package com.example.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseProvider extends ContentProvider {
    private MyDatabaseHelper helper;
    public static final int USER_DIR = 0;
    public static final int USER_ITEM = 1;
    public static final String AUTHORITY = "com.example.contentprovider.database.provider";
    public static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "userDemo", USER_DIR);
        uriMatcher.addURI(AUTHORITY, "userDemo/#", USER_ITEM);
    }

    public DatabaseProvider() {
        Log.e("abc", "DatabaseProvider: ");
    }

    @Override
    public boolean onCreate() {
        helper = new MyDatabaseHelper(getContext(), "userDBDemo", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                cursor = database.query("userDemo", strings, s, strings1, null, null, s1);
                break;
            case USER_ITEM:
                String queryId = uri.getPathSegments().get(1);
                cursor = database.query("userDemo", strings, "id=?", new String[]{queryId}, null, null, s1);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase database = helper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
            case USER_ITEM:
                long newUserId = database.insert("userDemo", null, contentValues);
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = helper.getReadableDatabase();
        int deleteInt = 0;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                deleteInt = database.delete("userDemo", s, strings);
                break;
            case USER_ITEM:
                String deleteId = uri.getPathSegments().get(1);
                deleteInt = database.delete("userDemo", "id=?", new String[]{deleteId});
                break;
            default:
                break;
        }
        return deleteInt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = helper.getReadableDatabase();
        int updateRow = 0;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                updateRow = database.update("userDemo", contentValues, s, strings);
                break;
            case USER_ITEM:
                String updateId = uri.getPathSegments().get(1);
                updateRow = database.update("userDemo", contentValues, "id=?", new String[]{updateId});
                break;
            default:
                break;
        }
        return updateRow;
    }
}
