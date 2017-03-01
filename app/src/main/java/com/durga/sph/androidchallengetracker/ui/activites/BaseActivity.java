package com.durga.sph.androidchallengetracker.ui.activites;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.durga.sph.androidchallengetracker.LearnAndroidTrackerApplication;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.utils.ConnectionUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindString;
import butterknife.BindView;


/**
 * Created by root on 2/6/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static boolean mTwoPane = true;
    protected boolean isAuthenticated;
    FirebaseAuth.AuthStateListener mAuthListener;
    @BindString(R.string.mysession_attr)
    String mySessionAttribute;
    String mySessionAttr;
    DatabaseReference mConnectedRef;
    Context mContext;
    FragmentManager mfragmentManager;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //for tab layout
    @Nullable
    @BindView(R.id.pager_main)
    ViewPager mviewPager;
    @Nullable
    @BindView(R.id.tabs)
    TabLayout mtabLayout;
    @BindString(R.string.level)
    String levelargs;
    @Nullable
    @BindView(R.id.mysession_button)
    ImageView mysessionButton;
    @BindView(R.id.main_coordinateLayout)
    CoordinatorLayout mCoordinatorLayout;
    Snackbar mSnackBar;
    String mTag;
    int mCode = 1;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mTag = this.getClass().getName();
        mfragmentManager = getFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    setAnalyticsUserId(user.getUid());
                } else {
                    // User is signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER)
                                    .build(),
                            mCode);
                }
            }
        };
        mConnectedRef = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.firebase_connectpath));
        mConnectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    dismissSnackBar();
                } else {
                    if (!ConnectionUtils.isConnected(getApplicationContext())) {
                        displaySnackBar(getResources().getString(R.string.offline_msg));
                    } else {
                        dismissSnackBar();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(mTag, getResources().getString(R.string.connect_listerner_canceled));
            }
        });
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls().detectNetwork().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectActivityLeaks().penaltyLog().build());
    }

    protected void openMySession() {
        Intent sessionIntent = new Intent(this, MySessionActivity.class);
        startActivity(sessionIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mCode) {
            if (resultCode == 0) {
                isAuthenticated = true;
                FirebaseUser user = mAuth.getCurrentUser();
            }
        }
    }

    protected void setupViewPager(FragmentStatePagerAdapter adapter) {
        if (mviewPager != null) {
            mviewPager.setAdapter(adapter);
            mtabLayout.setupWithViewPager(mviewPager);
        }
    }

    //for offline or error messages
    public void displaySnackBar(String message) {
        if (mCoordinatorLayout != null) {
            mSnackBar = Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
            mSnackBar.show();
        }
    }

    //onpause of the activity
    public void dismissSnackBar() {
        if (mSnackBar != null && mSnackBar.isShown()) {
            mSnackBar.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissSnackBar();
    }

    protected void logEvent(String event, Bundle bundle) {
        ((LearnAndroidTrackerApplication) getApplication()).getFirebaseAnalyticsInstance().logEvent(event, bundle);
    }

    private void setAnalyticsUserId(String username) {
        ((LearnAndroidTrackerApplication) getApplication()).getFirebaseAnalyticsInstance().setUserId(username);
    }
}
