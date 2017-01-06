package com.ikkong.adgo.widget.smrecyclerview;

import android.content.Context;
import android.util.AttributeSet;

import com.ikkong.adgo.widget.ptrrecyclerview.BaseRecyclerAdapter;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/12/8
 * Description:主要复写setAdapter
 */

public class SmRecyclerView extends SwipeMenuRecyclerView {
    private SwipeMenuCreator mSMCreator;
    private OnSwipeMenuItemClickListener mSMItemClickListener;
    
    public SmRecyclerView(Context context) {
        super(context);
    }

    public SmRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSMCreator = swipeMenuCreator;
        super.setSwipeMenuCreator(swipeMenuCreator);
    }

    @Override
    public void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        this.mSMItemClickListener = swipeMenuItemClickListener;
        super.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof BaseRecyclerAdapter) {
            BaseRecyclerAdapter menuAdapter = (BaseRecyclerAdapter) adapter;
            menuAdapter.setSwipeMenuCreator(new SwipeMenuCreator() {
                @Override
                public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                    if (mSMCreator != null) {
                        mSMCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);
                    }
                }
            });
            menuAdapter.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
                @Override
                public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                    if (mSMItemClickListener != null) {
                        mSMItemClickListener.onItemClick(closeable, adapterPosition, menuPosition, direction);
                    }
                }
            });
        }
        super.setAdapter(adapter);
    }
}
