package com.durga.sph.androidchallengetracker.network;

import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 2/11/17.
 */

public class ReviewQuestionsInterface extends FirebaseDatabaseInterface {

    DatabaseReference m_databaseReference;
    public ReviewQuestionsInterface(){
        super();
        mDatabaseReference = mFireBaseDatabase.getReference(Constants.REVIEWEQUES);
    }

    @Override
    public void getMoreQuestions(String user, IGetQuestionsInterface callback, String lastkey, int length, List<String> myquestions) {
        if(lastkey != null) {
            final Query queryRef = mDatabaseReference.orderByKey().startAt(lastkey).limitToFirst(length);
            getReviewQuestions(queryRef, user, callback, length, myquestions);
        }
    }

    @Override
    public void getQuestions(final String user, final IGetQuestionsInterface callback, int length, List<String> myquestions) {
        final Query queryRef = mDatabaseReference.orderByKey().limitToFirst(length);
        getReviewQuestions(queryRef,user, callback, length, myquestions);

    }

    private void getReviewQuestions(final Query queryRef, final String user, final IGetQuestionsInterface callback, final int length, final List<String> myquestions){
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TrackerQuestion> questionsList = new ArrayList<>();
                TrackerQuestion question = null;
                long count = dataSnapshot.getChildrenCount();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //if approved by more than 3 reviewers and the question is not marked spam then add it to the list of questions
                    if (!myquestions.contains(data.getKey()) && data.child(Constants.ISSPAM).getValue().equals(false) && !data.child(Constants.USERID).getValue().equals(user) &&  (long)data.child(Constants.UPVOTE).getValue()>= Constants.APPROVE_MIN_QUESTION_COUNT && (long)data.child(Constants.UPVOTE).getValue()<= Constants.APPROVE_MAX_QUESTION_COUNT) {
                            question = new TrackerQuestion((HashMap<String, Object>) data.getValue());
                            questionsList.add(question);
                    }
                    isInitialValueLoaded = false;
                    callback.onQuestionsReady(questionsList);
                    if (questionsList.size() > 0 && questionsList.size() < length && count >= length) {
                        TrackerQuestion lastQuestion = questionsList.get(questionsList.size() - 1);
                        getMoreQuestions(user, callback, lastQuestion.getId(), length - questionsList.size(), myquestions);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
