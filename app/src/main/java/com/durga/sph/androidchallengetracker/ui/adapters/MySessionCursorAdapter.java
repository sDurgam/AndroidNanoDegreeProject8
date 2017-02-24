package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 2/24/17.
 */

public class MySessionCursorAdapter extends SimpleCursorAdapter {

    private LayoutInflater inflater;

    public MySessionCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView desc=(TextView)view.findViewById(R.id.mydesc);
        TextView level=(TextView)view.findViewById(R.id.mylevel);
        desc.setText(cursor.getString(1));
        level.setText(String.format(Constants.LEVEL_CAPS_FORMATTER, cursor.getInt(2)));
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.mysession_question_cell, null);
    }
}
