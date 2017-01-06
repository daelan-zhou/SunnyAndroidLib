package com.ikkong.adgo.widget.fixfab;

import android.support.v7.widget.RecyclerView;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/6/24
 * Description:
 */
public abstract class RecyclerViewScrollDetectorN extends RecyclerView.OnScrollListener {
    private int mScrollThreshold;

    abstract void onScrollUp();

    abstract void onScrollDown();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (dy > 0) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }
}