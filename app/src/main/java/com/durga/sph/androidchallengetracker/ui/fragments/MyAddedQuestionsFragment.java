package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;

/**
 * Created by root on 1/30/17.
 */

public class MyAddedQuestionsFragment extends MyFragment{

    public static MyAddedQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        MyAddedQuestionsFragment fragment = new MyAddedQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MyAddedQuestionsFragment(){
        uri = MyProgressContract.MyProgressEntry.buildUriAdded();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.listquestions_fragment, container, false);
//        super.onCreateView(inflater, container, savedInstanceState);
//        return view;
//    }
}
