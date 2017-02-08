package com.durga.sph.androidchallengetracker.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.FirebaseDatabaseInterface;
import com.durga.sph.androidchallengetracker.MyRecyclerViewAdapter;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 2/3/17.
 */

public class BaseFragment extends Fragment {

    //Firebase variables
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    ChildEventListener mChildEventListener;
    FirebaseDatabaseInterface mFirebaseDatabaseInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabaseInterface = new FirebaseDatabaseInterface();
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
}
