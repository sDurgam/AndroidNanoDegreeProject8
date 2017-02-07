package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 1/30/17.
 */

public class MyReviewedQuestionsFragment extends Fragment {

    public static MyReviewedQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        MyReviewedQuestionsFragment fragment = new MyReviewedQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
