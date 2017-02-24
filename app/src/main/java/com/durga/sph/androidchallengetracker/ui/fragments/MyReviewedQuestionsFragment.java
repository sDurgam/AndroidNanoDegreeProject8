package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.adapters.MySessionCursorAdapterMy;

import butterknife.ButterKnife;

/**
 * Created by root on 1/30/17.
 */

public class MyReviewedQuestionsFragment extends MyFragment {

    public static MyReviewedQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        MyReviewedQuestionsFragment fragment = new MyReviewedQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MyReviewedQuestionsFragment(){
        uri = MyProgressContract.MyProgressEntry.buildUriReviewed();
       // projectionFields = new String[]{MyProgressContract.MyProgressEntry._ID, MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mysession_questionslayout, container, false);
        ButterKnife.bind(this, view);
        myquestionsTitle.setText(getResources().getString(R.string.reviewed_questions_name));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
