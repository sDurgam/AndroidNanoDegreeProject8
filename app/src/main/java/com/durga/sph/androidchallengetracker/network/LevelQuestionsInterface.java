package com.durga.sph.androidchallengetracker.network;

import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.utils.Constants;
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
    @Override
    public void getQuestions(String key, final String filter1, final String filter2, final IGetQuestionsInterface callback, int length){
        final Query queryRef = mDatabaseReference.child(key).orderByChild(Constants.LASTMODIFIED).limitToFirst(length);
        getQuestions(queryRef, key, filter1, filter2, callback, length);
    }

    @Override
    public void getMoreQuestions(String key, final String filter1, final String filter2, final IGetQuestionsInterface callback, String start, String lastkey, int length){
        final Query queryRef = mDatabaseReference.child(key).orderByChild(Constants.LASTMODIFIED).startAt(start, lastkey).limitToFirst(length);
        getQuestions(queryRef, key, filter1, filter2, callback, length);
    }


    public void getQuestions(Query queryRef, final String key, final String filter1, final String filter2, final IGetQuestionsInterface callback, final int length) {
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TrackerQuestion> questionsList = new ArrayList<>();
                TrackerQuestion question= null;
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    //if approved by more than 3 reviewers and the question is not marked spam then add it to the list of questions
                    if(data.child(filter1).getValue().toString().equals(filter2) && data.child(Constants.ISSPAM).getValue().equals(false) && data.child(Constants.UPVOTE).getChildrenCount() >= Constants.APPROVE_QUESTION_COUNT){
                        question = new TrackerQuestion((HashMap<String, Object>) data.getValue());
                        questionsList.add(question);
                    }
                }
                isInitialValueLoaded = true;
                callback.onQuestionsReady(questionsList);
                if(questionsList.size() > 0 && questionsList.size() < length){
                    TrackerQuestion lastQuestion = questionsList.get(questionsList.size()-1);
                    getMoreQuestions(key,filter1, filter2, callback, lastQuestion.getLastModified(), lastQuestion.getId(),length-questionsList.size());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
