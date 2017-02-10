package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.utils.Constants;

import butterknife.ButterKnife;

/**
 * Created by root on 2/9/17.
 */

public class ReviewQuestionDetailFragment extends BaseFragment {

    public static ReviewQuestionDetailFragment newInstance(String title, int level) {
        Bundle args = new Bundle();
        args.putString(Constants.TITLE, title);
        args.putInt(Constants.LEVEL, level);
        ReviewQuestionDetailFragment fragment = new ReviewQuestionDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View  view = inflater.inflate(R.layout.mypoints_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


}
