package com.durga.sph.androidchallengetracker.ui.activites;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.adapters.TabletViewFragmentPagerAdapter;
import com.durga.sph.androidchallengetracker.ui.adapters.TabletViewMySessionPagerAdapter;
import com.durga.sph.androidchallengetracker.ui.fragments.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyPointsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;

import butterknife.BindString;
import butterknife.BindView;
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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mysessionButton.setVisibility(View.GONE);
        FragmentStatePagerAdapter pagerAdapter = new TabletViewMySessionPagerAdapter(mFragmentManager, this);
        setupViewPager(pagerAdapter);
    }
}
