package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.durga.sph.androidchallengetracker.FirebaseDatabaseInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;

/**
 * Created by root on 2/3/17.
 */

public class BaseFragment extends Fragment {

    //Firebase variables
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    ChildEventListener mChildEventListener;
    protected FirebaseDatabaseInterface mFirebaseDatabaseInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabaseInterface = new FirebaseDatabaseInterface();
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
}
