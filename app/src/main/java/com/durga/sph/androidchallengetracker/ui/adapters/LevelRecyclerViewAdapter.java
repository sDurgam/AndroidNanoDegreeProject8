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
import com.durga.sph.androidchallengetracker.ui.listeners.IOnItemClickListener;

import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class LevelRecyclerViewAdapter extends RecyclerView.Adapter<LevelRecyclerViewAdapter.MyViewHolder> {

    Context m_context;
    List<TrackerQuestion> my_recipesList;
    final IOnItemClickListener itemClickListener;

    public LevelRecyclerViewAdapter(Context context, List<TrackerQuestion> recipesList, IOnItemClickListener listener){
        m_context = context;
        my_recipesList = recipesList;
        itemClickListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descriptionView;
        AppCompatCheckBox spamCheckedView;
        AppCompatCheckBox solvedOrReviewedView;
        public MyViewHolder(View itemView) {
            super(itemView);
            descriptionView = (TextView) itemView.findViewById(R.id.textView);
            spamCheckedView = (AppCompatCheckBox) itemView.findViewById(R.id.isspamView);
            solvedOrReviewedView = (AppCompatCheckBox) itemView.findViewById(R.id.issolvedorReviewedView);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view = LayoutInflater.from(m_context).inflate(R.layout.level_cell_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TrackerQuestion recipe = my_recipesList.get(position);
        holder.descriptionView.setText(recipe.title);
        if(holder.solvedOrReviewedView.isChecked()){
            holder.solvedOrReviewedView.setChecked(false);
        }
    }

    public int getItemCount() {
        return my_recipesList != null ? my_recipesList.size() : 0;
    }

    public void updateAdapter(List<TrackerQuestion> questions){
        my_recipesList.addAll(questions);
        notifyDataSetChanged();
    }


}
