package com.durga.sph.androidchallengetracker.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;

import butterknife.ButterKnife;

import static com.durga.sph.androidchallengetracker.ui.activites.BaseActivity.mTwoPane;

/**
 * Created by root on 1/30/17.
 */

public class MySolvedQuestionsFragment extends MyFragment {

    public MySolvedQuestionsFragment() {
        mUri = MyProgressContract.MyProgressEntry.buildUriSolved();
    }

    public static MySolvedQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        MySolvedQuestionsFragment fragment = new MySolvedQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mUri = MyProgressContract.MyProgressEntry.buildUriSolved();
        mProjectionFields = new String[]{MyProgressContract.MyProgressEntry._ID, MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL};
        mUibindForm = new String[]{MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL};
        mUibindTo = new int[]{R.id.mydesc, R.id.mylevel};
        mLayoutId = R.layout.mysession_question_cell;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mysession_questionslayout, container, false);
        ButterKnife.bind(this, view);
        if (!mTwoPane) {
            myquestionsTitle.setVisibility(View.VISIBLE);
            myquestionsTitle.setText(getResources().getString(R.string.solved_questions_title));
        }
        emptyView.setText(getResources().getString(R.string.solved_empty_list));
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
