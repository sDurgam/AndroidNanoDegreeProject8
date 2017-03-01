package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.utils.Constants;


/**
 * Created by root on 2/24/17.
 */

public class MyAddedCursorAdapter extends SimpleCursorAdapter {

    private LayoutInflater inflater;
    String approvedText;

    public MyAddedCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.approvedText = context.getResources().getString(R.string.marked_as_approved);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView desc=(TextView)view.findViewById(R.id.added_desc);
        TextView level=(TextView)view.findViewById(R.id.added_level);
        AppCompatCheckBox approved = (AppCompatCheckBox) view.findViewById(R.id.added_approved);
        approved.setText(approvedText);
        desc.setText(cursor.getString(1));
        level.setText(String.format(Constants.LEVEL_CAPS_FORMATTER, cursor.getInt(2)));
        if(cursor.getString(3).equals("1")){
            approved.setChecked(true);
        }
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.addedques_cell, null);
    }
}
