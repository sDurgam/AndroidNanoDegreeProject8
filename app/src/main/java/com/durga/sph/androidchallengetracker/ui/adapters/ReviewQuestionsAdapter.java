package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.view.View;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnItemClickListener;

import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class ReviewQuestionsAdapter extends BaseRecyclerViewAdapter {
    String m_user;
    public ReviewQuestionsAdapter(Context context, List<TrackerQuestion> recipesList, String user, IOnItemClickListener listener){
        super(context, recipesList, listener);
        m_user = user;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TrackerQuestion recipe = my_recipesList.get(position);
        super.onBindViewHolder(holder, position);
        holder.spamCheckedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display review question fragment
                itemClickListener.onisSpamClick(my_recipesList.get(position), position);
            }
        });
        holder.solvedOrReviewedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onisReviewedClick(my_recipesList.get(position), m_user, position);
            }
        });
    }

    public void addItem(TrackerQuestion question){
        my_recipesList.add(question);
        notifyItemInserted(my_recipesList.size()-1);
        notifyItemRangeChanged(my_recipesList.size()-1, getItemCount());
    }

    public void removeItem(int position){
        my_recipesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public int getItemCount() {
        return my_recipesList != null ? my_recipesList.size() : 0;
    }


    public int getItemById(String id) {
        boolean isfound = false;
        int position = 0;
        for(TrackerQuestion question : my_recipesList){
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
}
