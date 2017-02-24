package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 2/24/17.
 */

public class MySessionCursorAdapterMy extends MyBaseCursorAdapter {

    public MySessionCursorAdapterMy(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView desc=(TextView)view.findViewById(R.id.mydesc);
        TextView level=(TextView)view.findViewById(R.id.mylevel);
        desc.setText(cursor.getString(1));
        level.setText(String.format(Constants.LEVELFORMATTER, cursor.getInt(2)));
    }
}
