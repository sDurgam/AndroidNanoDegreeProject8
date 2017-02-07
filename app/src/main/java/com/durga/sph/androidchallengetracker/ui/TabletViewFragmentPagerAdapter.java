package com.durga.sph.androidchallengetracker.ui;


import android.app.Fragment;
import android.app.FragmentManager;

import com.durga.sph.androidchallengetracker.MyAddedQuestionsFragment;
import com.durga.sph.androidchallengetracker.MyPointsFragment;
import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ReviewQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.LevelFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.MyReviewedQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.NewQuestionFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 2/6/17.
 */

public class TabletViewFragmentPagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {

    String mlevelargs;
    public TabletViewFragmentPagerAdapter(FragmentManager fm, String levelargs) {
        super(fm);
        mlevelargs = levelargs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0){
            fragment = LevelFragment.newInstance(mlevelargs, 1);

        }else if(position == 1){
            fragment = LevelFragment.newInstance(mlevelargs, 2);
        }
        else if(position == 2){
            fragment = LevelFragment.newInstance(mlevelargs, 3);
        }
        else if(position == 3){
            fragment = NewQuestionFragment.newInstance();

        }else if(position == 4){
            fragment = ReviewQuestionsFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Level 1";

        }else if(position == 1){
            return "Level 2";
        }
        else if(position == 2){
            return "Level 3";
        }
        else if(position == 3){
            return "Add Question";

        }else if(position == 4){
            return "Review Question";
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return Constants.TAB_ITEM_COUNT;
    }
}
