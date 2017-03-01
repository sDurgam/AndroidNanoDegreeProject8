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

public class MyAddedQuestionsFragment extends MyFragment {

    public static MyAddedQuestionsFragment newInstance() {
        Bundle args = new Bundle();
        MyAddedQuestionsFragment fragment = new MyAddedQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mUri = MyProgressContract.MyProgressEntry.buildUriAdded();
        mProjectionFields = new String[]{MyProgressContract.MyProgressEntry._ID, MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL, MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED};
        mUibindForm = new String[]{MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, MyProgressContract.MyProgressEntry.COLUMN_LEVEL, MyProgressContract.MyProgressEntry.COLUMN_ISAPPROVED};
        mUibindTo = new int[]{R.id.added_desc, R.id.added_level, R.id.added_approved};
        mLayoutId = R.layout.addedques_cell;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mysession_questionslayout, container, false);
        ButterKnife.bind(this, view);
        if (!mTwoPane) {
            myquestionsTitle.setVisibility(View.VISIBLE);
            myquestionsTitle.setText(getResources().getString(R.string.added_questions_title));
        }
        emptyView.setText(getResources().getString(R.string.added_empty_list));
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
