package com.durga.sph.androidchallengetracker.activites;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.fragments.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyPointsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;

import butterknife.ButterKnife;

/**
 * Created by root on 2/6/17.
 */

public class MySessionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysession_activity);
        ButterKnife.bind(this);
        String attr = getIntent().getExtras().getString(m_mysessionattribute);
        loadSession(attr);
    }

    protected void loadSession(String sessionType){
        Fragment fragment = getFragmentManager().findFragmentById(R.id.mysessionfragment);
        Fragment newfragment = null;
        if(sessionType != null){
            if(sessionType.equals(Constants.MYPOINTS)){
                 newfragment = MyPointsFragment.newInstance();
            }
            else if(sessionType.equals(Constants.MYADDEDQUES)){
                newfragment = MyAddedQuestionsFragment.newInstance();
            }
            else if(sessionType.equals(Constants.MYREVIEWEDQUES)){
                newfragment = MyReviewedQuestionsFragment.newInstance();
            }
        }
        if(fragment == null){

        }
        else if(newfragment != null){
            getFragmentManager().beginTransaction().replace(R.id.mysessionfragment, newfragment).commit();
        }
    }
}
