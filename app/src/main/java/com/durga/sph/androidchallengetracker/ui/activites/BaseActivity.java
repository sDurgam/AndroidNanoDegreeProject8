package com.durga.sph.androidchallengetracker.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.fragments.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyPointsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindString;
import butterknife.OnClick;
import butterknife.Optional;


/**
 * Created by root on 2/6/17.
 */

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthListener;
    String mTAG;
    @BindString(R.string.mysession_attr) String m_mysessionattribute;
    protected boolean isAuthenticated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Optional @OnClick(R.id.mysession_points)
    protected void MySessionPointsClick(View view){
        Log.d("abc", "abc");
        if(this instanceof MySessionActivity)
        {
            getFragmentManager().beginTransaction().replace(R.id.mysessionfragment, MyPointsFragment.newInstance()).commit();
        }
        else {
            Intent myPointsIntent = new Intent(this, MySessionActivity.class);
            myPointsIntent.putExtra(m_mysessionattribute, Constants.MYPOINTS);
            startActivity(myPointsIntent);
        }
    }

    @Optional
    @OnClick(R.id.mysession_addedquestions)
    protected void MySessionAddedQuestionsClick(View view){
        if(this instanceof MySessionActivity)
        {
            getFragmentManager().beginTransaction().replace(R.id.mysessionfragment, MyAddedQuestionsFragment.newInstance()).commit();
        }
        else {
            Intent myaddedQuestionsIntent = new Intent(this, MySessionActivity.class);
            myaddedQuestionsIntent.putExtra(m_mysessionattribute, Constants.MYADDEDQUES);
            startActivity(myaddedQuestionsIntent);
        }
    }

    @Optional @OnClick(R.id.mysession_reviewedquestions)
    protected void MySessionReviewedQuestionsClick(View view){
        if(this instanceof MySessionActivity)
        {
            getFragmentManager().beginTransaction().replace(R.id.mysessionfragment, MyReviewedQuestionsFragment.newInstance()).commit();
        }
        else {
            Intent myreviewedQuesIntent = new Intent(this, MySessionActivity.class);
            myreviewedQuesIntent.putExtra(m_mysessionattribute, Constants.MYREVIEWEDQUES);
            startActivity(myreviewedQuesIntent);
        }
    }
}