package com.durga.sph.androidchallengetracker.ui.activites;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.adapters.TabletViewFragmentPagerAdapter;
import com.durga.sph.androidchallengetracker.ui.fragments.LevelFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyPointsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.NewQuestionFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.ReviewQuestionsFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity{

    @Nullable @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Nullable @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @Nullable @BindView(R.id.main_navigationView)
    NavigationView mNavigationView;
    @Nullable @BindView(R.id.pager_main)
    ViewPager mViewPager;
    @Nullable @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindString(R.string.level) String mlevelargs;
    FragmentManager mFragmentManager;
    static boolean mTwoPane = true;
    String m_mySessionAttr;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    int code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTAG = this.getClass().getName();
        setSupportActionBar(mToolbar);
        mFragmentManager = getFragmentManager();
        if(mViewPager == null){
            mTwoPane = false;
        }
        if(savedInstanceState == null){
            LevelFragment fragment = LevelFragment.newInstance(mlevelargs, 1);
            mFragmentManager.beginTransaction().add(R.id.main_frameLayout, fragment).commit();
        }
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    // User is signed out
                    onSignedOutCleanup();
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
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls().detectNetwork().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectActivityLeaks().penaltyLog().build());
        if(!mTwoPane) {
            setUpDrawerContent();
        }
        else{
            setupViewPager();
        }
    }

    //only for mobile devices and not tablets
    void setUpDrawerContent(){
        if(mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectDrawerItem(item.getItemId());
                            return false;
                        }
                    }
            );
        }
    }

    private void selectDrawerItem(int type){
        if(type == R.id.nav_points_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyPointsFragment.newInstance()).commit();

        }else if(type == R.id.nav_level01_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, LevelFragment.newInstance(mlevelargs, 1)).commit();

        }else if(type == R.id.nav_level02_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, LevelFragment.newInstance(mlevelargs, 2)).commit();

        }
        else if(type == R.id.nav_level03_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, LevelFragment.newInstance(mlevelargs, 3)).commit();
        }
        else if(type == R.id.nav_addquestion_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, NewQuestionFragment.newInstance()).commit();

        }else if(type == R.id.nav_reviewquestions_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, ReviewQuestionsFragment.newInstance()).commit();

        }else if(type == R.id.nav_myaddedquestions_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyAddedQuestionsFragment.newInstance()).commit();

        }else if(type == R.id.nav_myreviewedquestions_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyReviewedQuestionsFragment.newInstance()).commit();
        }
        mDrawerLayout.closeDrawers();
    }

    private void setupViewPager(){
        if(mViewPager != null) {
            TabletViewFragmentPagerAdapter pagerAdapter = new TabletViewFragmentPagerAdapter(mFragmentManager, mlevelargs);
            mViewPager.setAdapter(pagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    public void onResume(){
        super.onResume();
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
        if(requestCode == code){
            if(resultCode == 0){
                isAuthenticated = true;
                FirebaseUser user = mAuth.getCurrentUser();
            }
        }
    }

    private void onSignedInInitialize(String username) {

    }

    private void onSignedOutCleanup() {

    }
}
