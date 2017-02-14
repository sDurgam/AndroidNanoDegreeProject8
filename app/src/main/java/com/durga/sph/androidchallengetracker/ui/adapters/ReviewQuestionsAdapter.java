package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnReviewerItemClickListerner;

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
        AppCompatCheckBox spamCheckedView;
        AppCompatCheckBox approvedView;
        AppCompatCheckBox unapprovedView;
        public MyViewHolder(View itemView) {
            super(itemView);
            descriptionView = (TextView) itemView.findViewById(R.id.reviewer_textView);
            spamCheckedView = (AppCompatCheckBox) itemView.findViewById(R.id.reviewer_isspamView);
            approvedView = (AppCompatCheckBox) itemView.findViewById(R.id.isApprovedView);
            unapprovedView = (AppCompatCheckBox) itemView.findViewById(R.id.isNotApprovedView);
        }
    }

    @Override
    public BaseRecylerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view = LayoutInflater.from(m_context).inflate(R.layout.reviewer_cell_view, parent, false);
        return new ReviewQuestionsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseRecylerViewAdapter.MyViewHolder holder, int position) {
        final TrackerQuestion recipe = m_trackerQuestionsList.get(position);
        final int pos = position;
        ReviewQuestionsAdapter.MyViewHolder h = (ReviewQuestionsAdapter.MyViewHolder)holder;
        h.descriptionView.setText(recipe.getDescription());
        if(h.spamCheckedView.isChecked()){
            h.spamCheckedView.setChecked(false);
        }
        if(h.unapprovedView.isChecked()){
            h.unapprovedView.setChecked(false);
        }
        if(h.approvedView.isChecked()){
            h.approvedView.setChecked(false);
        }
        h.spamCheckedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display review question fragment
                m_itemClickListener.onisSpamClick(m_trackerQuestionsList.get(pos), pos);
            }
        });
        h.approvedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_itemClickListener.onisApprovedClick(m_trackerQuestionsList.get(pos), m_user, pos);
            }
        });
        h.unapprovedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_itemClickListener.onisNotApprovedClick(m_trackerQuestionsList.get(pos), m_user, pos);
            }
        });
    }

}
