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
    String m_user;
    IOnReviewerItemClickListerner m_itemClickListener;

    public ReviewQuestionsAdapter(Context context, List<TrackerQuestion> recipesList, String user, IOnReviewerItemClickListerner listener){
        super(context, recipesList);
        this.m_itemClickListener = listener;
        this.m_user = user;
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
    public void onBindViewHolder(final BaseRecylerViewAdapter.MyViewHolder holder, final int position) {
        final TrackerQuestion recipe = m_trackerQuestionsList.get(position);
        ReviewQuestionsAdapter.MyViewHolder h = (ReviewQuestionsAdapter.MyViewHolder)holder;
        h.descriptionView.setText(recipe.getTitle());
        if(h.spamCheckedView.isChecked()){
            h.spamCheckedView.setChecked(false);
        }
        h.spamCheckedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display review question fragment
                m_itemClickListener.onisSpamClick(m_trackerQuestionsList.get(position), position);
            }
        });
        h.approvedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_itemClickListener.onisApprovedClick(m_trackerQuestionsList.get(position), m_user, position);
            }
        });
        h.unapprovedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_itemClickListener.onisNotApprovedClick(m_trackerQuestionsList.get(position), m_user, position);
            }
        });
    }

}
