package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnReviewerItemClickListerner;
import com.durga.sph.androidchallengetracker.utils.Constants;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class ReviewQuestionsAdapter extends BaseRecylerViewAdapter {
    IOnReviewerItemClickListerner m_itemClickListener;

    public ReviewQuestionsAdapter(Context context, List<TrackerQuestion> recipesList, String user, IOnReviewerItemClickListerner listener){
        super(context, user, recipesList);
        this.m_itemClickListener = listener;
    }

    public class MyViewHolder extends BaseRecylerViewAdapter.MyViewHolder{
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

    @Override
    public BaseRecylerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view;
        view = LayoutInflater.from(m_context).inflate(R.layout.reviewer_cell_view, parent, false);
        return new ReviewQuestionsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseRecylerViewAdapter.MyViewHolder holder, int position) {
        final TrackerQuestion recipe = m_trackerQuestionsList.get(position);
        final int pos = position;
        ReviewQuestionsAdapter.MyViewHolder h = (ReviewQuestionsAdapter.MyViewHolder)holder;
        h.descriptionView.setText(recipe.getDescription());
        h.descriptionView.setContentDescription(m_context.getResources().getString(R.string.questiondesc) + recipe.getDescription());
        h.levelView.setText(String.format(Constants.LEVEL_CAPS_FORMATTER, recipe.level));
        h.levelView.setContentDescription(String.format(Constants.LEVEL_CAPS_FORMATTER, recipe.level));
        h.markGroup.clearCheck();
        h.markGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.reviewer_isspam){
                    //display review question fragment
                    m_itemClickListener.onisSpamClick(m_trackerQuestionsList.get(pos), pos);}
                else if(checkedId == R.id.reviewer_approved){
                    m_itemClickListener.onisApprovedClick(m_trackerQuestionsList.get(pos), m_user, pos);
                }
                else {
                    m_itemClickListener.onisNotApprovedClick(m_trackerQuestionsList.get(pos), m_user, pos);
                }
                group.setContentDescription(m_context.getResources().getString (R.string.selected) + ((RadioButton)group.findViewById(checkedId)).getText());
            }
        });
    }
}
