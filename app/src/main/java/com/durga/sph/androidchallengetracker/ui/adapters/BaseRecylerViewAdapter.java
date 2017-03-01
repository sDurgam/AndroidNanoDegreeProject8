package com.durga.sph.androidchallengetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 2/12/17.
 */

public abstract class BaseRecylerViewAdapter extends RecyclerView.Adapter<BaseRecylerViewAdapter.MyViewHolder> {
    Context context;
    List<TrackerQuestion> trackerQuestionsList;
    Set<String> questionIDsSet;
    String user;
    private boolean isUpdating = false;

    public BaseRecylerViewAdapter(Context context, String user, List<TrackerQuestion> my_trackerQuestionsList){
        this.context = context;
        this.trackerQuestionsList = my_trackerQuestionsList;
        this.user = user;
        this.questionIDsSet = new HashSet<>();
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
        if(questionIDsSet.contains(question.id)) return;
        trackerQuestionsList.add(question);
        questionIDsSet.add(question.id);
        notifyItemInserted(trackerQuestionsList.size()-1);
        notifyItemRangeChanged(trackerQuestionsList.size()-1, getItemCount());
    }

    public void removeItem(String questionId){
        int position = -1;
        for(int i = 0; i < trackerQuestionsList.size(); i++){
            if(trackerQuestionsList.get(i).id.equals(questionId)){
                position = i;
                break;
            }
        }
        trackerQuestionsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeItem(int position){
        if(position != -1) {
            trackerQuestionsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public int getItemCount() {
        if(trackerQuestionsList == null) return 0;
        return trackerQuestionsList.size();
    }


    public int getItemById(String id) {
        boolean isfound = false;
        int position = 0;
        for(TrackerQuestion question : trackerQuestionsList){
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
        TrackerQuestion question = null;
        for(;i < end && start >= 0 && end <= questions.size(); i++) {
            question = questions.get(i);
            if(!questionIDsSet.contains(question.id)) {
                trackerQuestionsList.add(question);
                questionIDsSet.add(question.id);
            }
        }
        if(i > start) {
            notifyDataSetChanged();
        }
    }

    public String getQuestionIdByPosition(int position){
        return position <= trackerQuestionsList.size() ? trackerQuestionsList.get(position).id : null;
    }

    public TrackerQuestion getQuestionById(String id){
        for(int i = 0; i < trackerQuestionsList.size(); i++){
            if(trackerQuestionsList.get(i).id.equals(id)){
                return trackerQuestionsList.get(i);
            }
        }
        return null;
    }

    public boolean isExistsQuestion(String id){
        if(trackerQuestionsList == null || trackerQuestionsList.size() <= 0) return false;
        return trackerQuestionsList.get(getItemCount()-1).id.equals(id);
    }
    public void setisUpdating(boolean isupdating){
        isUpdating = isupdating;
    }

    public boolean getisUpdating(){
        return isUpdating;
    }

    public int findPos(TrackerQuestion question){
        for(int i = 0; i < trackerQuestionsList.size(); i++){
            if(trackerQuestionsList.get(i).id.equals(question.id)){
                return i;
            }
        }
        return -1;
    }
}
