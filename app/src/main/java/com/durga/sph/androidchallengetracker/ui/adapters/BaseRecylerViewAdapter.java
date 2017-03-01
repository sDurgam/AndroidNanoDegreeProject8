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
    Context mContext;
    List<TrackerQuestion> mTrackerQuestionsList;
    Set<String> mQuestionIDsSet;
    String mUser;
    private boolean mIsUpdating = false;

    public BaseRecylerViewAdapter(Context mContext, String mUser, List<TrackerQuestion> my_trackerQuestionsList) {
        this.mContext = mContext;
        this.mTrackerQuestionsList = my_trackerQuestionsList;
        this.mUser = mUser;
        this.mQuestionIDsSet = new HashSet<>();
    }

    @Override
    public BaseRecylerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void addItem(TrackerQuestion question) {
        if (mQuestionIDsSet.contains(question.id)) return;
        mTrackerQuestionsList.add(question);
        mQuestionIDsSet.add(question.id);
        notifyItemInserted(mTrackerQuestionsList.size() - 1);
        notifyItemRangeChanged(mTrackerQuestionsList.size() - 1, getItemCount());
    }

    public void removeItem(String questionId) {
        int position = -1;
        for (int i = 0; i < mTrackerQuestionsList.size(); i++) {
            if (mTrackerQuestionsList.get(i).id.equals(questionId)) {
                position = i;
                break;
            }
        }
        mTrackerQuestionsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeItem(int position) {
        if (position != -1) {
            mTrackerQuestionsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public int getItemCount() {
        if (mTrackerQuestionsList == null) return 0;
        return mTrackerQuestionsList.size();
    }

    public int getItemById(String id) {
        boolean isfound = false;
        int position = 0;
        for (TrackerQuestion question : mTrackerQuestionsList) {
            if (question.getId().equals(id)) {
                isfound = true;
                break;
            }
            position++;
        }
        if (!isfound) {
            position = -1;
        }
        return position;
    }

    public void updateAdapter(List<TrackerQuestion> questions, int start, int end) {
        int i = start;
        TrackerQuestion question = null;
        for (; i < end && start >= 0 && end <= questions.size(); i++) {
            question = questions.get(i);
            if (!mQuestionIDsSet.contains(question.id)) {
                mTrackerQuestionsList.add(question);
                mQuestionIDsSet.add(question.id);
            }
        }
        if (i > start) {
            notifyDataSetChanged();
        }
    }

    public String getQuestionIdByPosition(int position) {
        return position <= mTrackerQuestionsList.size() ? mTrackerQuestionsList.get(position).id : null;
    }

    public TrackerQuestion getQuestionById(String id) {
        for (int i = 0; i < mTrackerQuestionsList.size(); i++) {
            if (mTrackerQuestionsList.get(i).id.equals(id)) {
                return mTrackerQuestionsList.get(i);
            }
        }
        return null;
    }

    public boolean isExistsQuestion(String id) {
        if (mTrackerQuestionsList == null || mTrackerQuestionsList.size() <= 0) return false;
        return mTrackerQuestionsList.get(getItemCount() - 1).id.equals(id);
    }

    public void setisUpdating(boolean isupdating) {
        mIsUpdating = isupdating;
    }

    public boolean getisUpdating() {
        return mIsUpdating;
    }

    public int findPos(TrackerQuestion question) {
        for (int i = 0; i < mTrackerQuestionsList.size(); i++) {
            if (mTrackerQuestionsList.get(i).id.equals(question.id)) {
                return i;
            }
        }
        return -1;
    }

    public abstract class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
