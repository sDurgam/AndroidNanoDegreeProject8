package com.durga.sph.androidchallengetracker.ui.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.fragments.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyPointsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MySolvedQuestionsFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 2/23/17.
 */

public class TabletViewMySessionPagerAdapter extends FragmentStatePagerAdapter {

    Context context;

    public TabletViewMySessionPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        context = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0){
            fragment = MyPointsFragment.newInstance();

        }else if(position == 1){
            fragment = MySolvedQuestionsFragment.newInstance();
        }
        else if(position == 2){
            fragment = MyAddedQuestionsFragment.newInstance();
        }
        else if(position == 3) {
            fragment = MyReviewedQuestionsFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return context.getResources().getString(R.string.mypoints_name);

        }else if(position == 1){
            return context.getResources().getString(R.string.solved_questions_name);
        }
        else if(position == 2){
            return context.getResources().getString(R.string.added_questions_name);
        }
        else if(position == 3){
            return context.getResources().getString(R.string.reviewed_questions_name);

        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return Constants.TAB_ITEM_MYSESSION_COUNT;
    }
}
