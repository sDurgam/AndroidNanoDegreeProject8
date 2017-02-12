package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
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

public class ReviewQuestionsAdapter extends RecyclerView.Adapter<ReviewQuestionsAdapter.MyViewHolder> {
    Context m_context;
    String m_user;
    List<TrackerQuestion> m_reviewQuestionsList;
    final IOnReviewerItemClickListerner m_itemClickListener;

    public ReviewQuestionsAdapter(Context context, List<TrackerQuestion> recipesList, String user, IOnReviewerItemClickListerner listener){
        m_context = context;
        m_user = user;
        m_itemClickListener = listener;
        m_reviewQuestionsList = recipesList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
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

    public ReviewQuestionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view = LayoutInflater.from(m_context).inflate(R.layout.reviewer_cell_view, parent, false);
        return new ReviewQuestionsAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TrackerQuestion recipe = m_reviewQuestionsList.get(position);
        holder.descriptionView.setText(recipe.getTitle());
        if(holder.spamCheckedView.isChecked()){
            holder.spamCheckedView.setChecked(false);
        }
        holder.spamCheckedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display review question fragment
                m_itemClickListener.onisSpamClick(m_reviewQuestionsList.get(position), position);
            }
        });
        holder.approvedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_itemClickListener.onisApprovedClick(m_reviewQuestionsList.get(position), m_user, position);
            }
        });
        holder.unapprovedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_itemClickListener.onisNotApprovedClick(m_reviewQuestionsList.get(position), m_user, position);
            }
        });
    }

    public void addItem(TrackerQuestion question){
        m_reviewQuestionsList.add(question);
        notifyItemInserted(m_reviewQuestionsList.size()-1);
        notifyItemRangeChanged(m_reviewQuestionsList.size()-1, getItemCount());
    }

    public void removeItem(int position){
        m_reviewQuestionsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public int getItemCount() {
        return m_reviewQuestionsList != null ? m_reviewQuestionsList.size() : 0;
    }


    public int getItemById(String id) {
        boolean isfound = false;
        int position = 0;
        for(TrackerQuestion question : m_reviewQuestionsList){
            if(question.getId().equals(id)){
                isfound =true;
                break;
            }
            position++;
        }
        if(!isfound) {
            position = -1;
        }
        return position;
    }

    public void updateAdapter(List<TrackerQuestion> questions){
        m_reviewQuestionsList.addAll(questions);
        notifyDataSetChanged();
    }
}
