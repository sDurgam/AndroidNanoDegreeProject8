package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.providers.MyProgressContract;

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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
