package com.durga.sph.androidchallengetracker.ui.activites;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.DPadDrawerLayout;
import com.durga.sph.androidchallengetracker.ui.adapters.TabletViewFragmentPagerAdapter;
import com.durga.sph.androidchallengetracker.ui.fragments.LevelFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyPointsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MySolvedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.NewQuestionFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.ReviewQuestionsFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


public class MainActivity extends BaseActivity{

    @Nullable @BindView(R.id.drawer_main)
    DPadDrawerLayout drawerLayout;
    @Nullable @BindView(R.id.main_navigationView)
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    boolean fromWidget = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinateLayout);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        if(viewPager == null){
            mTwoPane = false;
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getString(Constants.FROMWIDGET) != null){
            fromWidget = true;
        }
        if(savedInstanceState == null){
            LevelFragment fragment = LevelFragment.newInstance(levelargs, 1);
            fragmentManager.beginTransaction().add(R.id.main_frameLayout, fragment).commit();
        }

        if(!mTwoPane) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
            NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            navMenuView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
            setUpDrawerContent();
            if(fromWidget){
                selectDrawerItem(R.id.nav_points_fragment);
            }
        }
        else{
            FragmentStatePagerAdapter pagerAdapter = new TabletViewFragmentPagerAdapter(fragmentManager, levelargs, this);
            setupViewPager(pagerAdapter);
        }
    }

    //only for mobile devices and not tablets
    void setUpDrawerContent(){

        if(navigationView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                navigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                        return insets;
                    }
                });
            }
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            navigationView.setCheckedItem(item.getItemId());
                            item.setChecked(true);
                            menuItemId = item;
                            selectDrawerItem(item.getItemId());
                            return true;
                        }
                    }
            );
            navigationView.setAccessibilityDelegate(new View.AccessibilityDelegate());
        }
    }

    private void selectDrawerItem(int type){
        if(type == R.id.nav_points_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyPointsFragment.newInstance()).commit();

        }else if(type == R.id.nav_level01_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, LevelFragment.newInstance(levelargs, 1)).commit();

        }else if(type == R.id.nav_level02_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, LevelFragment.newInstance(levelargs, 2)).commit();

        }
        else if(type == R.id.nav_level03_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, LevelFragment.newInstance(levelargs, 3)).commit();
        }
        else if(type == R.id.nav_addquestion_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, NewQuestionFragment.newInstance()).commit();

        }else if(type == R.id.nav_reviewquestions_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, ReviewQuestionsFragment.newInstance()).commit();

        }
        else if(type == R.id.nav_solvedquestions_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, MySolvedQuestionsFragment.newInstance()).commit();

        }else if(type == R.id.nav_myaddedquestions_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyAddedQuestionsFragment.newInstance()).commit();

        }else if(type == R.id.nav_myreviewedquestions_fragment){
            fragmentManager.beginTransaction().replace(R.id.main_frameLayout, MyReviewedQuestionsFragment.newInstance()).commit();
        }
        drawerLayout.closeDrawers();
    }

    public void onResume(){
        super.onResume();
    }

    MenuItem menuItemId;

    @Optional
    @OnClick(R.id.mysession_button)
    protected void MySessionClick(View view){
        openMySession();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
