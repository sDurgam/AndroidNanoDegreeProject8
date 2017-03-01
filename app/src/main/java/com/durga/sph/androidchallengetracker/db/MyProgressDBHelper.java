package com.durga.sph.androidchallengetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.durga.sph.androidchallengetracker.providers.MyProgressContract;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "myprogress";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_UPVOTE = "upvote";
    public static final String COLUMN_ISSOLVED = "solved";
    public static final String COLUMN_ISREVIEWED = "reviewed";
    public static final String COLUMN_ISADDED = "added";
    public static final String COLUMN_LASTMODIFIED = "lastmodified";
    public MyProgressDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String MYPROGRESS_CREATE_TABLE = "CREATE TABLE " + MyProgressContract.MyProgressEntry.TABLE_NAME + "(" +
                MyProgressContract.MyProgressEntry._ID + " VARCHAR(50) PRIMARY KEY NOT NULL," +
                MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                MyProgressContract.MyProgressEntry.COLUMN_LEVEL + " INTEGER DEFAULT 1," +
                MyProgressContract.MyProgressEntry.COLUMN_UPVOTE + " INTEGER DEFAULT 0," +
                MyProgressContract.MyProgressEntry.COLUMN_ISSOLVED + " INTEGER DEFAULT 0," +
                MyProgressContract.MyProgressEntry.COLUMN_ISREVIEWED + " INTEGER DEFAULT 0," +
                MyProgressContract.MyProgressEntry.COLUMN_ISADDED + " INTEGER DEFAULT 0," +
                MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED + " INTEGER DEFAULT 0," +
                MyProgressContract.MyProgressEntry.COLUMN_CREATEDATE + " TEXT NOT NULL," +
                MyProgressContract.MyProgressEntry.COLUMN_LASTMODIFIED + " TEXT" + ")";

        db.execSQL(MYPROGRESS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + MyProgressContract.MyProgressEntry.TABLE_NAME);
    }
}
