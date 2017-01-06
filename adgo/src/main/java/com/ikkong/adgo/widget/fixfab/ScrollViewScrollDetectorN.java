package com.ikkong.adgo.widget.fixfab;

import android.widget.ScrollView;

import com.melnykov.fab.ObservableScrollView;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/6/24
 * Description:
 */
public abstract class ScrollViewScrollDetectorN  implements ObservableScrollView.OnScrollChangedListener {
    private int mLastScrollY;
    private int mScrollThreshold;

    abstract void onScrollUp();

    abstract void onScrollDown();

    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        boolean isSignificantDelta = Math.abs(t - mLastScrollY) > mScrollThreshold;
        if (isSignificantDelta) {
            if (t > mLastScrollY) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
        mLastScrollY = t;
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }
}