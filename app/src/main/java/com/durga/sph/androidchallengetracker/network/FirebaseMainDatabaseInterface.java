package com.durga.sph.androidchallengetracker.network;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by root on 2/23/17.
 */

public class FirebaseMainDatabaseInterface {
    //Firebase variables
    FirebaseDatabase mFireBaseDatabase;
    DatabaseReference mDatabaseReference;
    ChildEventListener mquestionChildEventListener;
    boolean misInitialValueLoaded = true;
    String mTag;


    public FirebaseMainDatabaseInterface() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference();
        mTag = this.getClass().getName();
    }
}
