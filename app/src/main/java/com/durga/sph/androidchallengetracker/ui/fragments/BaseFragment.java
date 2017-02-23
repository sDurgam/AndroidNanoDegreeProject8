package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.network.FirebaseDatabaseInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.providers.MyProgressContract;
import com.durga.sph.androidchallengetracker.ui.adapters.BaseRecylerViewAdapter;
import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;

import java.util.List;

/**
 * Created by root on 2/3/17.
 */

public class BaseFragment extends Fragment implements IGetQuestionsInterface{

    //Firebase variables
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    ChildEventListener mChildEventListener;
    protected FirebaseDatabaseInterface mFirebaseDatabaseInterface;
    String m_lastQuestionId = null;
    String m_lasttimeStamp = null;
    String m_username;
    BaseRecylerViewAdapter m_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    m_username = user.getUid();
                }
                else{

                }
            }
        };
    }

    protected String userName(){
        return mFirebaseAuth.getCurrentUser().getUid();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        super.onPause();
    }

//    public void registerFirebaseChildListener(String key){
//        mFirebaseDatabaseInterface.registerQuestionsByLevelEventListener(key, );
//    }
//
//    public void unregisterFirebaseChildListener(String key){
//        mFirebaseDatabaseInterface.unregisterEventListener(key);
//    }

    @Override
    public void onQuestionsReady(List<TrackerQuestion> questionsList) {
        if(questionsList != null && questionsList.size() > 0) {
            int index = 0;
            TrackerQuestion question = questionsList.get(0);
            if(m_adapter.isExistsQuestion(question.id))
            {
                index= 1;
            }
            if(questionsList.size() >= index+1){
                m_lastQuestionId = questionsList.get(questionsList.size()-1).id;
            }
            else{
                m_lastQuestionId = null;
            }
           // m_lasttimeStamp = question.lastModified.get(Constants.LASTMODIFIED);
            m_adapter.updateAdapter(questionsList, index, questionsList.size());
        }
        else{
            //log it
        }
    }

    protected void displayToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void addToDatabase(TrackerQuestion newQuestion, String param) {
        ContentValues values = new ContentValues();
        values.put(MyProgressContract.MyProgressEntry._ID, newQuestion.id);
        values.put(MyProgressContract.MyProgressEntry.COLUMN_LEVEL, newQuestion.level);
        values.put(MyProgressContract.MyProgressEntry.COLUMN_DESCRIPTION, newQuestion.description);
        values.put(MyProgressContract.MyProgressEntry.COLUMN_CREATEDATE, String.valueOf(newQuestion.dateCreated));
        values.put(param, 1);
        getActivity().getContentResolver().insert(MyProgressContract.MyProgressEntry.CONTENT_URI, values);
    }

    protected void updateDatabase(String id, String param) {
        if(id != null) {
            ContentValues values = new ContentValues();
            values.put(param, 1);
            getActivity().getContentResolver().update(MyProgressContract.MyProgressEntry.buildUriByID(id), values, null, null);
        }else{
            Log.e(getClass().getName(), getResources().getString(R.string.action_failed));
            displayToastMessage(getResources().getString(R.string.action_failed));
        }
    }
}
