package com.durga.sph.androidchallengetracker.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.durga.sph.androidchallengetracker.ui.listeners.IGetQuestionsInterface;

/**
 * Created by root on 1/10/17.
 */

public abstract class RecylclerViewEndlessScrollListener extends RecyclerView.OnScrollListener {

    IGetQuestionsInterface m_callback;
    LinearLayoutManager mlayoutManager;
    int mvisibleTreshold = 5;
    int mpreviousItemCount;
    int mcurrentPage;
    boolean misLoading = true;


    public RecylclerViewEndlessScrollListener(IGetQuestionsInterface callback, LinearLayoutManager layoutManager)
    {
        super();
        mlayoutManager = layoutManager;
        m_callback = callback;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisisblePosition = 0;
        int currentPage = 0;
        int totalCount = mlayoutManager.getItemCount();
        if(mlayoutManager instanceof LinearLayoutManager)
        {
            lastVisisblePosition = ((LinearLayoutManager)mlayoutManager).findLastVisibleItemPosition();
        }
        if(totalCount < mpreviousItemCount) //totalcount is less than existing items count
        {
            mcurrentPage = 0;
            mpreviousItemCount = totalCount;
            if(totalCount == 0)
            {
                misLoading = true;
            }
        }
        if(misLoading && totalCount > mpreviousItemCount)   //update previouscount to totalcount as new items are loaded as user scrolls the list
        {
            mpreviousItemCount = totalCount;
            misLoading = false;
        }
        if(!misLoading && (lastVisisblePosition + mvisibleTreshold) > totalCount)   //get more items via api request
        {
            misLoading = true;
            mcurrentPage ++;
            onLoadMore(m_callback);
        }
    }
    //any subclass needs to implement its functionality to load more items in recyclerview
    public abstract void onLoadMore(IGetQuestionsInterface callback);

}
