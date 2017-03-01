package com.durga.sph.androidchallengetracker.ui.activites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.adapters.TabletViewMySessionPagerAdapter;

import butterknife.ButterKnife;

/**
 * Created by root on 2/6/17.
 */

public class MySessionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.my_progress));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mysessionButton.setVisibility(View.GONE);
        FragmentStatePagerAdapter pagerAdapter = new TabletViewMySessionPagerAdapter(fragmentManager, this);
        setupViewPager(pagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }
}
