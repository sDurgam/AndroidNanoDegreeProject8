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

public class ReviewQuestionsInterface extends FirebaseDatabaseInterface {

    public ReviewQuestionsInterface(){
        super();
    }

    @Override
    public void getMoreQuestions(String key, String user, IGetQuestionsInterface callback, String start, String lastkey, int length) {
        final Query queryRef = mDatabaseReference.child(key).orderByChild(Constants.LASTMODIFIED).startAt(start, lastkey).limitToFirst(length);
        getReviewQuestions(queryRef, key, user, callback, length);
    }

    @Override
    public void getQuestions(final String key, final String user, final IGetQuestionsInterface callback, int length) {
        final Query queryRef = mDatabaseReference.child(key).orderByChild(Constants.LASTMODIFIED).limitToFirst(length);
        getReviewQuestions(queryRef,key,user, callback, length);

    }

    private void getReviewQuestions(final Query queryRef, final String key, final String user, final IGetQuestionsInterface callback, final int length){
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TrackerQuestion> questionsList = new ArrayList<>();
                TrackerQuestion question= null;
                long count = dataSnapshot.getChildrenCount();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    //if approved by more than 3 reviewers and the question is not marked spam then add it to the list of questions
                    if(data.child(Constants.ISSPAM).getValue().equals(false) && !data.child(Constants.USERID).getValue().equals(user) && (data.child(Constants.REVIEWER).getValue() == null || !((List<String>)data.child(Constants.REVIEWER).getValue()).contains(user)) && data.child(Constants.REVIEWER).getChildrenCount() >= Constants.APPROVE_MIN_QUESTION_COUNT && data.child(Constants.REVIEWER).getChildrenCount() <= Constants.APPROVE_MAX_QUESTION_COUNT){
                        //if(data.child(Constants.ISSPAM).getValue().equals(false)  && data.child(Constants.REVIEWER).getChildrenCount() <= Constants.APPROVE_MAX_QUESTION_COUNT){
                        question = new TrackerQuestion((HashMap<String, Object>) data.getValue(), true);
                        questionsList.add(question);
                    }
                }
                isInitialValueLoaded = false;
                callback.onQuestionsReady(questionsList);
                if(questionsList.size() > 0 && questionsList.size() < length && count >= length){
                    TrackerQuestion lastQuestion = questionsList.get(questionsList.size()-1);
                    getMoreQuestions(key,user,callback, lastQuestion.getLastModified(), lastQuestion.getId(),length-questionsList.size());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
