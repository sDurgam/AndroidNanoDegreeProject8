package com.durga.sph.androidchallengetracker.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by root on 1/10/17.
 */

public abstract class RecylclerViewEndlessScrollListener extends RecyclerView.OnScrollListener {

    Context mContext;
    int m_totalCount;
    int m_prevItemCount;
    int m_visibleThreshold = 5;
    boolean m_isLoading = false;
    int currentPage = 1;
    public RecylclerViewEndlessScrollListener(Context context){
        mContext = context;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = ((LinearLayoutManager) recyclerView.getLayoutManager());
            m_totalCount =manager.findLastVisibleItemPosition();
            if(m_totalCount < m_prevItemCount + m_visibleThreshold){  //load everything
                m_prevItemCount = m_totalCount;
                if(m_totalCount == 0){
                    m_isLoading = true;
                }
            }
            else if(m_isLoading && m_totalCount > m_prevItemCount){
                m_prevItemCount = m_totalCount;
                m_isLoading = false;
            }
            else if(!m_isLoading && m_totalCount > m_prevItemCount + m_visibleThreshold){
                m_isLoading = true;
                onLoadMore(currentPage);
                currentPage++;
            }
        }
    }
    public abstract void onLoadMore(int currentPage);
}
