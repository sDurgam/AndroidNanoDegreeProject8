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

    FirebaseAuth.AuthStateListener authListener;
    String tag;
    @BindString(R.string.mysession_attr) String mySessionAttribute;
    protected boolean isAuthenticated;
    public static boolean mTwoPane = true;
    String mySessionAttr;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    DatabaseReference connectedRef;
    int code=1;
    Context context;
    FragmentManager fragmentManager;
    @Nullable @BindView(R.id.toolbar)
    Toolbar toolbar;
    //for tab layout
    @Nullable @BindView(R.id.pager_main)
    ViewPager viewPager;
    @Nullable @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindString(R.string.level) String levelargs;
    @Nullable @BindView(R.id.mysession_button)
    ImageView mysessionButton;
    @BindView(R.id.main_coordinateLayout)
    CoordinatorLayout coordinatorLayout;
    Snackbar snackBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        tag = this.getClass().getName();
        fragmentManager = getFragmentManager();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
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
                            code);
                }
            }
        };
        connectedRef = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.firebase_connectpath));
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    dismissSnackBar();
                } else {
                    if(!ConnectionUtils.isConnected(getApplicationContext())) {
                        displaySnackBar(getResources().getString(R.string.offline_msg));
                    }
                    else{
                        dismissSnackBar();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(tag, getResources().getString(R.string.connect_listerner_canceled));
            }
        });
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls().detectNetwork().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectActivityLeaks().penaltyLog().build());
    }

    protected void openMySession(){
        Intent sessionIntent = new Intent(this, MySessionActivity.class);
        startActivity(sessionIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == code){
            if(resultCode == 0){
                isAuthenticated = true;
                FirebaseUser user = auth.getCurrentUser();
            }
        }
    }

    protected void setupViewPager(FragmentStatePagerAdapter adapter){
        if(viewPager != null) {
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    //for offline or error messages
    public void displaySnackBar(String message)
    {
        if(coordinatorLayout != null) {
            snackBar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
            snackBar.show();
        }
    }

    //onpause of the activity
    public void dismissSnackBar()
    {
        if(snackBar != null && snackBar.isShown())
        {
            snackBar.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissSnackBar();
    }

    protected void logEvent(String event, Bundle bundle){
        ((LearnAndroidTrackerApplication)getApplication()).getFirebaseAnalyticsInstance().logEvent(event, bundle);
    }

    private void setAnalyticsUserId(String username){
        ((LearnAndroidTrackerApplication)getApplication()).getFirebaseAnalyticsInstance().setUserId(username);
    }
}
