package com.durga.sph.androidchallengetracker.ui.activites;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.adapters.TabletViewFragmentPagerAdapter;
import com.durga.sph.androidchallengetracker.ui.fragments.LevelFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyPointsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MySolvedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.NewQuestionFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.ReviewQuestionsFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity{

    @Nullable @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @Nullable @BindView(R.id.main_navigationView)
    NavigationView mNavigationView;
    boolean fromWidget = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(mToolbar);
        ButterKnife.bind(this);
        if(mViewPager == null){
            mTwoPane = false;
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getString(Constants.FROMWIDGET) != null){
            fromWidget = true;
        }
        if(savedInstanceState == null){
            LevelFragment fragment = LevelFragment.newInstance(mlevelargs, 1);
            mFragmentManager.beginTransaction().add(R.id.main_frameLayout, fragment).commit();
        }

        if(!mTwoPane) {
            NavigationMenuView navMenuView = (NavigationMenuView) mNavigationView.getChildAt(0);
            navMenuView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
            setUpDrawerContent();
            if(fromWidget){
                selectDrawerItem(R.id.nav_points_fragment);
            }
        }
        else{
            if(fromWidget){
                openMySession();
            }
            else {
                FragmentStatePagerAdapter pagerAdapter = new TabletViewFragmentPagerAdapter(mFragmentManager, mlevelargs, this);
                setupViewPager(pagerAdapter);
            }
        }
    }

    //only for mobile devices and not tablets
    void setUpDrawerContent(){
        if(mNavigationView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                mNavigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                        return insets;
                    }
                });
            }
            mNavigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            item.setChecked(true);
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

        }
        else if(type == R.id.nav_solvedquestions_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, MySolvedQuestionsFragment.newInstance()).commit();

        }else if(type == R.id.nav_myaddedquestions_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyAddedQuestionsFragment.newInstance()).commit();

        }else if(type == R.id.nav_myreviewedquestions_fragment){
            mFragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyReviewedQuestionsFragment.newInstance()).commit();
        }
        mDrawerLayout.closeDrawers();
    }

    public void onResume(){
        super.onResume();
    }

}
