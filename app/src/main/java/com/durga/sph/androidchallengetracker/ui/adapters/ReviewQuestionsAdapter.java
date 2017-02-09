package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class ReviewQuestionsAdapter extends BaseRecyclerViewAdapter {
    String m_user;
    public ReviewQuestionsAdapter(Context context, List<TrackerQuestion> recipesList, String user){
        super(context, recipesList);
        m_user = user;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TrackerQuestion recipe = my_recipesList.get(position);
        holder.m_textView.setText(recipe.title);
        if(m_user != null && recipe.reviewers.contains(m_user)){
            holder.m_textView.setChecked(true);
        }
    }

    public int getItemCount() {
        return my_recipesList != null ? my_recipesList.size() : 0;
    }

}
