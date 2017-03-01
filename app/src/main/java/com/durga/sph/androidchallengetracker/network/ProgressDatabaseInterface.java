package com.durga.sph.androidchallengetracker.network;

import android.util.Log;

import com.durga.sph.androidchallengetracker.ui.listeners.ProgressListener;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2/12/17.
 */

public class ProgressDatabaseInterface extends FirebaseMainDatabaseInterface {

    public ProgressDatabaseInterface() {
        mDatabaseReference = mFireBaseDatabase.getReference(Constants.PROGRESS);
    }

    public void getProgress(final ProgressListener listener) {
        final Map<String, Long> progressMap = new HashMap<>();
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    progressMap.put(data.getKey(), (Long) data.getValue());
                }
                listener.onProgressReceived(progressMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(mTag, databaseError.getMessage());
            }
        });
    }

    public void registerListener(ChildEventListener listener) {
        mDatabaseReference.addChildEventListener(listener);
    }

    public void unregisterListerner(ChildEventListener listener) {
        mDatabaseReference.removeEventListener(listener);
    }
}
