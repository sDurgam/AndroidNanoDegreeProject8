package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;

import com.durga.sph.androidchallengetracker.providers.MyProgressContract;

/**
 * Created by root on 1/30/17.
 */

public class MySolvedQuestionsFragment extends MyFragment {

    public static MySolvedQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        MySolvedQuestionsFragment fragment = new MySolvedQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MySolvedQuestionsFragment(){
        uri = MyProgressContract.MyProgressEntry.buildUriSolved();
    }
}
