package com.durga.sph.androidchallengetracker.network;

import android.util.Log;

import com.durga.sph.androidchallengetracker.ui.listeners.GetQuestionsInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 2/11/17.
 */

public class LevelQuestionsInterface extends FirebaseDatabaseInterface {

    public LevelQuestionsInterface(String level){
        mDatabaseReference = mFireBaseDatabase.getReference(level);
    }

    @Override
    public void getQuestions(String user, final GetQuestionsInterface callback, int length, List<String> myquestions){
        final Query queryRef = mDatabaseReference.orderByKey().limitToFirst(length);
        getLevelQuestions(user, queryRef, callback, length, myquestions);
    }

    @Override
    public void getMoreQuestions(String user, final GetQuestionsInterface callback, String start, int length, List<String> myquestions){
        final Query queryRef = mDatabaseReference.orderByKey().startAt(start).limitToFirst(length);
        getLevelQuestions(user, queryRef, callback, length, myquestions);
    }


    public void getLevelQuestions(final String user, Query queryRef, final GetQuestionsInterface callback, final int length, final List<String> myquestions) {
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TrackerQuestion> questionsList = new ArrayList<>();
                long count = dataSnapshot.getChildrenCount();
                TrackerQuestion question= null;
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    //if approved by more than 3 reviewers and the question is not marked spam then add it to the list of questions
                    //if not already solved by the user
                    if(!myquestions.contains(data.getKey()))
                    {
                        question = new TrackerQuestion((HashMap<String, Object>) data.getValue());
                        questionsList.add(question);
                    }
                }
                isInitialValueLoaded = true;
                callback.onQuestionsReady(questionsList);
                if (questionsList.size() > 0 && questionsList.size() < length && count >= length) {
                    TrackerQuestion lastQuestion = questionsList.get(questionsList.size()-1);
                    getMoreQuestions(user, callback, lastQuestion.getId(),length-questionsList.size(), myquestions);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(this.getClass().getName(), databaseError.getMessage());
            }
        });
    }
}
