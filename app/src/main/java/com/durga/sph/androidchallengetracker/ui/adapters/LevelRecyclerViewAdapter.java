package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.R;
import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnLevelItemClickListerner;

import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class LevelRecyclerViewAdapter extends BaseRecylerViewAdapter {

    IOnLevelItemClickListerner m_itemClickListener;

    public LevelRecyclerViewAdapter(Context context, List<TrackerQuestion> recipesList, String user, IOnLevelItemClickListerner listener){
        super(context, user, recipesList);
        this.m_itemClickListener = listener;
    }

    public class MyViewHolder extends BaseRecylerViewAdapter.MyViewHolder{
        TextView descriptionView;
        AppCompatCheckBox solvedView;
        public MyViewHolder(View itemView) {
            super(itemView);
            descriptionView = (TextView) itemView.findViewById(R.id.textView);
            solvedView = (AppCompatCheckBox) itemView.findViewById(R.id.issolvedorReviewedView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view;
        view = LayoutInflater.from(m_context).inflate(R.layout.level_cell_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecylerViewAdapter.MyViewHolder holder, int position) {
        final TrackerQuestion recipe = m_trackerQuestionsList.get(position);
        final int pos = position;
        LevelRecyclerViewAdapter.MyViewHolder h = (LevelRecyclerViewAdapter.MyViewHolder)holder;
        h.descriptionView.setText(recipe.description);
        h.solvedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_itemClickListener.onisSolvedClick(recipe.id, m_user, pos);
            }
        });
    }
}
