package com.durga.sph.androidchallengetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

/**
 * Created by root on 2/13/17.
 */

public class MyProgressDBHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public MyProgressDBHelper(Context context, String dbname){
        super(context, dbname, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
