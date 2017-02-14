package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;
import com.durga.sph.androidchallengetracker.ui.listeners.IOnItemClickListener;

import java.util.List;

/**
 * Created by root on 2/12/17.
 */

public abstract class BaseRecylerViewAdapter extends RecyclerView.Adapter<BaseRecylerViewAdapter.MyViewHolder> {
    Context m_context;
    List<TrackerQuestion> m_trackerQuestionsList;
    String m_user;

    public BaseRecylerViewAdapter(Context context, String user, List<TrackerQuestion> my_trackerQuestionsList){
        this.m_context = context;
        this.m_trackerQuestionsList = my_trackerQuestionsList;
        this.m_user = user;
    }

    @Override
    public BaseRecylerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public abstract class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView){
            super(itemView);
        }
    }

    public void addItem(TrackerQuestion question){
        m_trackerQuestionsList.add(question);
        notifyItemInserted(m_trackerQuestionsList.size()-1);
        notifyItemRangeChanged(m_trackerQuestionsList.size()-1, getItemCount());
    }

    public void removeItem(int position){
        m_trackerQuestionsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public int getItemCount() {
        return m_trackerQuestionsList != null ? m_trackerQuestionsList.size() : 0;
    }


    public int getItemById(String id) {
        boolean isfound = false;
        int position = 0;
        for(TrackerQuestion question : m_trackerQuestionsList){
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

    public void updateAdapter(List<TrackerQuestion> questions, int start, int end){
        int i = start;
        for(;i < end && start >= 0 && end <= questions.size(); i++) {
            m_trackerQuestionsList.add(questions.get(i));
        }
        if(i > start) {
            notifyDataSetChanged();
        }
    }

    public String getQuestionIdByPosition(int position){
        return position <= m_trackerQuestionsList.size() ? m_trackerQuestionsList.get(position).id : null;
    }

    public boolean isExistsQuestion(String id){
        if(m_trackerQuestionsList == null || m_trackerQuestionsList.size() <= 0) return false;
        return m_trackerQuestionsList.get(getItemCount()-1).id.equals(id);
    }
}