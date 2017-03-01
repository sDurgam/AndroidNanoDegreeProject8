package com.durga.sph.androidchallengetracker;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by root on 2/27/17.
 */

public class LearnAndroidTrackerApplication extends Application {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        getFirebaseAnalyticsInstance();
    }

    public FirebaseAnalytics getFirebaseAnalyticsInstance() {
        if (mFirebaseAnalytics == null) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        }
        return mFirebaseAnalytics;
    }
}
