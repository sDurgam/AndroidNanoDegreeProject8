package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.adapters.MyBaseCursorAdapter;
import com.durga.sph.androidchallengetracker.ui.adapters.MySessionCursorAdapterMy;

import butterknife.BindView;

/**
 * Created by root on 2/22/17.
 */

public class MyFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int MYPROGRESS_LOADER = 100;
    Uri uri;
    private SimpleCursorAdapter adapter;
    @Nullable  @BindView(R.id.mysession_listView)
    ListView myquestionsView;
    Cursor cursor;

    @Nullable @BindView(R.id.mysession_title)
    TextView myquestionsTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(MYPROGRESS_LOADER, new Bundle(), this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projectionFields = new String[]{MyProgressContract.MyProgressEntry._ID, MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION};
        CursorLoader loader = new CursorLoader(this.getActivity(), uri, projectionFields, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpCursorAdapter();
    }

    private void setUpCursorAdapter(){
        String[] uibindForm = {MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION};
        int[] uibindTo = {R.id.textView};
        adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.level_cell_view, null, uibindForm, uibindTo, 0);
        myquestionsView.setAdapter(adapter);
    }
}
