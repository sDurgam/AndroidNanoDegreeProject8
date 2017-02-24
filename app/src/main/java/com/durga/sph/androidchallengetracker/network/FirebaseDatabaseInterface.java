package com.durga.sph.androidchallengetracker.network;

import android.util.Log;

import com.durga.sph.androidchallengetracker.orm.MyProgressQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;
import com.durga.sph.androidchallengetracker.ui.listeners.IListener;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnItemClickListener;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnLevelItemClickListerner;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnQuestionAddedListener;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnReviewerItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StreamDownloadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 2/8/17.
 */

public abstract class FirebaseDatabaseInterface extends FirebaseMainDatabaseInterface{

    public String getNewId(String key){
        DatabaseReference pushedQuestionRef = mDatabaseReference.child(key).push();
        // Get the unique ID generated by a push()
        return pushedQuestionRef.getKey();
    }

    public void addNewQuestion(TrackerQuestion question, final IOnQuestionAddedListener callback){
        DatabaseReference levelref = mDatabaseReference.child(Constants.REVIEWEQUES);
        final String key = levelref.push().getKey();
        question.id = key;
        levelref.child(key).setValue(question, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    Log.e(TAG, databaseError.toString());
                }
                callback.issuccess(databaseError== null, key);
            }
        });
        //update progress reviewquestions count

        mFireBaseDatabase.getReference(Constants.PROGRESS+"/"+Constants.REVIEWEQUES).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Long count = null;
                count = mutableData.getValue(Long.class);
                if(count == null) count = 0l;
                count ++;
                mutableData.setValue(count);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                if(databaseError != null){
                    Log.e(TAG, databaseError.getMessage());
                }
            }
        });
}

    public void markQuestionAsSpam(String questionId, final IOnReviewerItemClickListerner listener) {
        mFireBaseDatabase.getReference(Constants.REVIEWEQUES+"/"+questionId+"/"+Constants.ISSPAM).setValue(true, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                // Transaction completed
                if(databaseError != null){
                    Log.e(TAG, databaseError.getMessage());
                }
                listener.isSuccess(databaseError == null);
            }
        });
    }

    public void markQuestionAsApproved(final String questionId, final String mlevel, final boolean isApproved, final IOnReviewerItemClickListerner listener){
        Query queryRef = mFireBaseDatabase.getReference(Constants.REVIEWEQUES+"/"+questionId+"/"+Constants.UPVOTE);
        long vote = 0l;
        queryRef.getRef().runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue() != null) {
                    long upvote = mutableData.getValue(Long.class);
                    if (isApproved) upvote++;
                    else upvote--;
                    mutableData.setValue(upvote);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                if(databaseError == null){
                    listener.isSuccess(true);
                    if(dataSnapshot.getValue () != null && (long)dataSnapshot.getValue() >= Constants.APPROVE_QUESTION_COUNT){
                        moveFirebaseRecord(Constants.REVIEWEQUES+"/"+questionId, mlevel);
                    }
                }
            }
        });
    }

    public void moveFirebaseRecord(final String fromPathString, final String level)
    {
        final DatabaseReference fromPath = mFireBaseDatabase.getReference(fromPathString);
        fromPath.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String key = dataSnapshot.getKey();
                String val = dataSnapshot.child(Constants.LEVEL).getValue().toString();
                DatabaseReference toPath = mFireBaseDatabase.getReference(String.format(Constants.LEVELIDFORMATTER,val, key));
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener()
                {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null){
                            fromPath.removeValue();
                            //update progress review questions count
                            mFireBaseDatabase.getReference(Constants.PROGRESS+"/"+Constants.REVIEWEQUES).runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    Long count = null;
                                    count = mutableData.getValue(Long.class);
                                    if(count != null) {
                                        count--;
                                        mutableData.setValue(count);
                                    }
                                    return Transaction.success(mutableData);
                                }

                                @Override
                                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                    if(databaseError != null){
                                        Log.e(TAG, databaseError.getMessage());
                                    }
                                }
                            });
                            //update progress respective level count
                            mFireBaseDatabase.getReference(Constants.PROGRESS+"/"+level).runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    Long count = null;
                                    count = mutableData.getValue(Long.class);
                                    if(count == null) count = 0l;
                                    count ++;
                                    mutableData.setValue(count);
                                    return Transaction.success(mutableData);
                                }

                                @Override
                                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                    if(databaseError != null){
                                        Log.e(TAG, databaseError.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    //Fetch questions for review, myadded and myreviewed questions
    public void  getMoreQuestions(String user, IGetQuestionsInterface callback, String lastkey, int length, List<String> myquestions){
    }

    //Fetch questions for review, myadded and myreviewed questions
    public void getQuestions(String user, final IGetQuestionsInterface callback, int length, List<String> myquestions){

    }

    public void addQuestion(){};

public void registerEventListener(ChildEventListener listener) {
        mDatabaseReference.addChildEventListener(listener);
        }

public void unregisterEventListener(ChildEventListener listener) {
        mDatabaseReference.removeEventListener(listener);
        }
        }
