package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.MyViewHolder> {

    Context m_context;
    List<TrackerQuestion> my_recipesList;

    public BaseRecyclerViewAdapter(Context context, List<TrackerQuestion> recipesList){
        m_context = context;
        my_recipesList = recipesList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckedTextView m_textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            m_textView = (CheckedTextView) itemView.findViewById(R.id.textView);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view = LayoutInflater.from(m_context).inflate(R.layout.cell_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TrackerQuestion recipe = my_recipesList.get(position);
        holder.m_textView.setText(recipe.title);
    }

    public int getItemCount() {
        return my_recipesList != null ? my_recipesList.size() : 0;
    }

    public void updateAdapter(List<TrackerQuestion> questions){
        my_recipesList.addAll(questions);
        notifyDataSetChanged();
    }
}
