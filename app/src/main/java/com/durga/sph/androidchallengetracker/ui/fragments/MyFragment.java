package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.adapters.MyAddedCursorAdapter;
import com.durga.sph.androidchallengetracker.ui.adapters.MySessionCursorAdapter;

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
    int layoutId;

    String[] projectionFields;
    String[] uibindForm;
    int[] uibindTo;

    @Nullable @BindView(R.id.mysession_title)
    TextView myquestionsTitle;
    @BindView(R.id.emptySessionView)
    TextView emptyView;
    @BindView(R.id.loadingBar)
    ProgressBar loadingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(MYPROGRESS_LOADER, new Bundle(), this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this.getActivity(), uri, projectionFields, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        loadingBar.setVisibility(View.GONE);
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
        if(this instanceof MyAddedQuestionsFragment){
         adapter = new MyAddedCursorAdapter(this.getActivity(), layoutId, null, uibindForm, uibindTo, 0);
        }
        else {
            adapter = new MySessionCursorAdapter(this.getActivity(), layoutId, null, uibindForm, uibindTo, 0);
        }
        myquestionsView.setAdapter(adapter);
        myquestionsView.setEmptyView(emptyView);
    }
}
