package com.durga.sph.androidchallengetracker.ui.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentUris;
import android.content.Context;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.ui.fragments.ReviewQuestionsFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.LevelFragment;
import com.durga.sph.androidchallengetracker.ui.fragments.NewQuestionFragment;
import com.durga.sph.androidchallengetracker.utils.Constants;

/**
 * Created by root on 2/6/17.
 */

public class TabletViewFragmentPagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {

    String mlevelargs;
    Context m_context;
    public TabletViewFragmentPagerAdapter(FragmentManager fm, String levelargs, Context context) {
        super(fm);
        mlevelargs = levelargs;
        m_context = context;
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
            return m_context.getResources().getString(R.string.level1_name);

        }else if(position == 1){
            return m_context.getResources().getString(R.string.level2_name);
        }
        else if(position == 2){
            return m_context.getResources().getString(R.string.level3_name);
        }
        else if(position == 3){
            return m_context.getResources().getString(R.string.add_question_name);

        }else if(position == 4){
            return m_context.getResources().getString(R.string.review_questions_name);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return Constants.TAB_ITEM_COUNT;
    }
}
