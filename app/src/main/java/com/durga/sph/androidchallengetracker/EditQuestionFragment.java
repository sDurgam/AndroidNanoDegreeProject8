package com.durga.sph.androidchallengetracker;

import android.app.Fragment;
import android.os.Bundle;
import android.webkit.ConsoleMessage;

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


}
