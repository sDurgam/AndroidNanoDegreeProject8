package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.OnReviewerItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;

import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class ReviewQuestionsAdapter extends BaseRecylerViewAdapter {

    OnReviewerItemClickListerner mItemClickListener;

    public ReviewQuestionsAdapter(Context context, List<TrackerQuestion> recipesList, String user, OnReviewerItemClickListerner listener) {
        super(context, user, recipesList);
        this.mItemClickListener = listener;
    }

    @Override
    public BaseRecylerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.reviewer_cell_view, parent, false);
        return new ReviewQuestionsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseRecylerViewAdapter.MyViewHolder holder, int position) {
        final TrackerQuestion recipe = mTrackerQuestionsList.get(position);
        ReviewQuestionsAdapter.MyViewHolder h = (ReviewQuestionsAdapter.MyViewHolder) holder;
        h.descriptionView.setText(recipe.getDescription());
        h.descriptionView.setContentDescription(mContext.getResources().getString(R.string.questiondesc) + recipe.getDescription());
        h.levelView.setText(String.format(Constants.LEVEL_CAPS_FORMATTER, recipe.level));
        h.levelView.setContentDescription(String.format(Constants.LEVEL_CAPS_FORMATTER, recipe.level));
        h.markGroup.clearCheck();
        h.markGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.reviewer_isspam) {
                    //display review question fragment
                    mItemClickListener.onisSpamClick(recipe, group);
                } else if (checkedId == R.id.reviewer_approved) {
                    mItemClickListener.onisApprovedClick(recipe, group);
                } else {
                    mItemClickListener.onisNotApprovedClick(recipe, group);
                }
                if (checkedId != -1) {
                    group.setContentDescription(mContext.getResources().getString(R.string.selected) + ((RadioButton) group.findViewById(checkedId)).getText());
                }
            }
        });
    }

    public class MyViewHolder extends BaseRecylerViewAdapter.MyViewHolder {
        TextView descriptionView;
        AppCompatRadioButton levelView;
        RadioGroup markGroup;

        public MyViewHolder(View itemView) {
            super(itemView);
            descriptionView = (TextView) itemView.findViewById(R.id.reviewer_quesdesc);
            levelView = (AppCompatRadioButton) itemView.findViewById(R.id.reviewer_textLevel);
            markGroup = (RadioGroup) itemView.findViewById(R.id.reviewer_optionsGroup);
        }
    }
}
