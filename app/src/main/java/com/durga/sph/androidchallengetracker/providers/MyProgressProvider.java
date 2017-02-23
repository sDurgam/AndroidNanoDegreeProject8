package com.durga.sph.androidchallengetracker.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.UnsupportedSchemeException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.db.MyProgressDBHelper;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressProvider extends ContentProvider {

    private static final int MYPROGRESS = 1;

    static final int MYPROGRESS_URL = 100;
    static final int MYPROGRESS_WITH_SOLVED = 101;
    static final int MYPROGRESS_WITH_REVIEWED = 102;
    static final int MYPROGRESS_WITH_ADDED = 103;
    static final int MYPROGRESS_WITH_APPROVED = 104;
    static final int MYPROGRESS_WITH_LEVEL = 105;
    static final int MYPROGRESS_WITH_ID = 106;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    MyProgressDBHelper m_dbHelper;
    String TAG;

    public MyProgressProvider(){
        TAG = getClass().getName();
    }

    @Override
    public boolean onCreate() {
        m_dbHelper = new MyProgressDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case MYPROGRESS_URL:
                if(uri.getQuery() != null) {
                    String[] params = uri.getQuery().split("=");
                    if (params != null && params.length == 2) {
                        selection = params[0] + "=?";
                        selectionArgs = new String[]{params[1]};
                    }
                }
                retCursor = m_dbHelper.getReadableDatabase().query(MyProgressContract.MyProgressEntry.TABLE_NAME, projection,selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(Constants.UNKNOWN_URL + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case MYPROGRESS:
                return MyProgressContract.MyProgressEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException(Constants.UNKNOWN_URL + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        long id = -1;
        switch (match){
            case MYPROGRESS_URL:
                id = m_dbHelper.getWritableDatabase().insertWithOnConflict(MyProgressContract.MyProgressEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;
            default:
                Log.e(TAG, getContext().getResources().getString(R.string.insert_error));
                throw new UnsupportedOperationException(Constants.UNKNOWN_URL + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int id = -1;
        switch (match) {
            case MYPROGRESS_URL:
                id = m_dbHelper.getWritableDatabase().delete(MyProgressContract.MyProgressEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                Log.e(TAG, getContext().getResources().getString(R.string.delete_error));
                throw new UnsupportedOperationException(Constants.UNKNOWN_URL + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int id = -1;
        switch (match) {
            case MYPROGRESS_URL:
                id = m_dbHelper.getWritableDatabase().update(MyProgressContract.MyProgressEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MYPROGRESS_WITH_ID:
                selection = MyProgressContract.MyProgressEntry._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                id = m_dbHelper.getWritableDatabase().update(MyProgressContract.MyProgressEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                Log.e(TAG, getContext().getResources().getString(R.string.update_error));
                throw new UnsupportedOperationException(Constants.UNKNOWN_URL + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MyProgressContract.CONTENT_AUTHORITY;
        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MyProgressContract.PATH_MYPROGRESS, MYPROGRESS_URL);
        matcher.addURI(authority, MyProgressContract.PATH_MYPROGRESS + "/#", MYPROGRESS_WITH_ID);
        matcher.addURI(authority, MyProgressContract.PATH_MYPROGRESS + "/*/" + MyProgressContract.MyProgressEntry.COLUMN_LEVEL +"/*", MYPROGRESS_WITH_LEVEL);
        return matcher;
    }
}
