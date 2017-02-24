package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

/**
 * Created by root on 2/24/17.
 */

public abstract class MyBaseCursorAdapter extends SimpleCursorAdapter {
    Context mContext;
    Context appContext;
    int layout;
    Cursor cr;
    LayoutInflater inflater;

    public MyBaseCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, 0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }
}
