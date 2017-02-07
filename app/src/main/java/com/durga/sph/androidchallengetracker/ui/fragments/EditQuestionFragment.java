package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 1/30/17.
 */

public class EditQuestionFragment extends Fragment {
    public static EditQuestionFragment newInstance(String questionId){
        EditQuestionFragment fragment = new EditQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.QUESTIONID, questionId);
        fragment.setArguments(bundle);
        return fragment;
    }

    String mQuestionId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editquestion_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
