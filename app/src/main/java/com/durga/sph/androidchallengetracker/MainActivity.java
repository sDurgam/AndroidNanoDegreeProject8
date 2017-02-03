package com.durga.sph.androidchallengetracker;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_navigationView)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
       /* if(savedInstanceState == null){
            LevelFragment fragment = new LevelFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }*/
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls().detectNetwork().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectActivityLeaks().penaltyLog().build());
        setUpDrawerContent();
    }

    void setUpDrawerContent(){
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item.getItemId());
                        return false;
                    }
                }
        );
    }

    private void selectDrawerItem(int type){
        if(type == 1){

        }else if(type == 2){

        }else if(type == 3){

        }else if(type == 4){

        }else if(type == 5){

        }else if(type == 6){

        }else if(type == 7){

        }else{

        }
    }

    public void onResume(){
        super.onResume();
    }


}
