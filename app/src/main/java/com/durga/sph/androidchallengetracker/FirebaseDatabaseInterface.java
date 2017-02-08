package com.durga.sph.androidchallengetracker;

import android.util.Log;
import android.webkit.HttpAuthHandler;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2/8/17.
 */

public class FirebaseDatabaseInterface {

    //Firebase variables
    FirebaseDatabase mFireBaseDatabase;
    DatabaseReference mDatabaseReference;
    ChildEventListener mquestionChildEventListener;

    public FirebaseDatabaseInterface(){
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child(Constants.TRACKER);
    }

    public String getNewId(String key){
        DatabaseReference pushedQuestionRef = mDatabaseReference.child(key).push();
        // Get the unique ID generated by a push()
        return pushedQuestionRef.getKey();
    }

    public void addNewItem(String key, String newId, Object value){
        mDatabaseReference.child(key).child(newId).setValue(value);
    }

    public void registerEventListener(String key){
        mquestionChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.child(key).addChildEventListener(mquestionChildEventListener);
    }

    public void unregisterEventListener(String key){
        mDatabaseReference.child(key).removeEventListener(mquestionChildEventListener);
    }

    public void getQuestions(String key, final String filter1, final String filter2, final IGetQuestionsInterface callback){
        final Query queryRef = mDatabaseReference.child(key);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TrackerQuestion> questionsList = new ArrayList<>();
                TrackerQuestion question= null;
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.child(filter1).getValue().toString().equals(filter2)){
                       question = new TrackerQuestion((HashMap<String, Object>) data.getValue());
                       questionsList.add(question);
                        Log.d("tag", data.getValue().toString());
                    }
                }
                callback.onQuestionsReady(questionsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
