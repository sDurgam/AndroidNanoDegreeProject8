package com.durga.sph.androidchallengetracker.network;

import com.durga.sph.androidchallengetracker.IGetQuestionsInterface;
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
    public void getQuestions(String key, String filter1, String filter2, IGetQuestionsInterface callback, String start, int length) {
        super.getQuestions(key, filter1, filter2, callback, start, length);
    }

    public void getQuestions(final String key, final String filter1, final String filter2, final IGetQuestionsInterface callback) {
        final Query queryRef = mDatabaseReference.child(key);
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
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
