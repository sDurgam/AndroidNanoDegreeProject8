package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        uri = MyProgressContract.MyProgressEntry.buildUriReviewed();
        projectionFields = new String[]{MyProgressContract.MyProgressEntry._ID, MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL};
        uibindForm = new String[] {MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL};
        uibindTo = new int[] {R.id.mydesc, R.id.mylevel};
        layoutId = R.layout.mysession_question_cell;
        super.onCreate(savedInstanceState);
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
