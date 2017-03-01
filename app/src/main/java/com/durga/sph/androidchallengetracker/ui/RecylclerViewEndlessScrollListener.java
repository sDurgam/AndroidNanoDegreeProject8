package com.durga.sph.androidchallengetracker.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.durga.sph.androidchallengetracker.ui.listeners.GetQuestionsInterface;

/**
 * Created by root on 1/10/17.
 */

public abstract class RecylclerViewEndlessScrollListener extends RecyclerView.OnScrollListener {

    GetQuestionsInterface mCallback;
    LinearLayoutManager mLayoutManager;
    int mVisibleTreshold = 5;
    int mPreviousItemCount;
    int mCurrentPage;
    boolean mIsLoading = true;


    public RecylclerViewEndlessScrollListener(GetQuestionsInterface callback, LinearLayoutManager layoutManager) {
        super();
        mLayoutManager = layoutManager;
        mCallback = callback;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisisblePosition = 0;
        int currentPage = 0;
        int totalCount = mLayoutManager.getItemCount();
        if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisisblePosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }
        if (totalCount < mPreviousItemCount) //totalcount is less than existing items count
        {
            mCurrentPage = 0;
            mPreviousItemCount = totalCount;
            if (totalCount == 0) {
                mIsLoading = true;
            }
        }
        if (mIsLoading && totalCount > mPreviousItemCount)   //update previouscount to totalcount as new items are loaded as user scrolls the list
        {
            mPreviousItemCount = totalCount;
            mIsLoading = false;
        }
        if (!mIsLoading && (lastVisisblePosition + mVisibleTreshold) > totalCount)   //get more items via api request
        {
            mIsLoading = true;
            mCurrentPage++;
            onLoadMore(mCallback);
        }
    }

    //any subclass needs to implement its functionality to load more items in recyclerview
    public abstract void onLoadMore(GetQuestionsInterface callback);

}
