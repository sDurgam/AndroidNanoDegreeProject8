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
import java.util.Set;

/**
 * Created by root on 2/11/17.
 */

public class MyReviewedQuestionsInterface extends FirebaseDatabaseInterface {

    public void getQuestions(String key, final String user, final IGetQuestionsInterface callback) {
        final Query queryRef = mDatabaseReference.child(key);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<TrackerQuestion> questionsList = new ArrayList<>();
                TrackerQuestion question= null;
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    //if approved by more than 3 reviewers and the question is not marked spam then add it to the list of questions
                    if(data.child(Constants.ISSPAM).getValue().equals(false) &&  ((Set<String>)data.child(Constants.REVIEWER).getValue()).contains(user)){
                        //question = new TrackerQuestion((HashMap<String, Object>) data.getValue(), true);
                       // questionsList.add(question);
                    }
                }
               // callback.onQuestionsReady(questionsList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
