package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.adapters.MyAddedCursorAdapterMy;

import butterknife.ButterKnife;

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
        //projectionFields = new String[]{MyProgressContract.MyProgressEntry._ID, MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL, MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mysession_questionslayout, container, false);
        ButterKnife.bind(this, view);
        myquestionsTitle.setText(getResources().getString(R.string.added_questions_name));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] uibindForm = new String[] {MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL, MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED};
    }
}
