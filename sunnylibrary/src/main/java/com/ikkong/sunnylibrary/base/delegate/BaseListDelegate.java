package com.ikkong.sunnylibrary.base.delegate;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.ikkong.sunnylibrary.R;
import com.ikkong.sunnylibrary.utils.DensityUtils;
import com.kymjs.frame.view.AppDelegate;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 列表基类视图
 *
 */
public class BaseListDelegate extends AppDelegate {

    protected RecyclerView mRecyclerView;
    protected PtrClassicFrameLayout ptrLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.ptr_recyclerview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ptrLayout= get(R.id.ptr_layout);
        // header
        final MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getActivity().getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtils.dip2px(getActivity(),15), 0, DensityUtils.dip2px(getActivity(),10));
        header.setPtrFrameLayout(ptrLayout);

        ptrLayout.setLoadingMinTime(1000);
        ptrLayout.setDurationToCloseHeader(1500);
        ptrLayout.setHeaderView(header);
        ptrLayout.addPtrUIHandler(header);
        ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrLayout.autoRefresh(false);
            }
        }, 100);
        ptrLayout.setDurationToClose(100);
        ptrLayout.setPinContent(true);

        mRecyclerView = get(R.id.recyler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
    }

    protected void setEmptyLayout(View emptyLayout) {
        FrameLayout layout = get(R.id.empty_layout);
        layout.addView(emptyLayout);
    }

    public void setSwipeRefreshLoadingState() {
//        SwipeRefreshLayout refreshLayout = get(R.id.swiperefreshlayout);
//        refreshLayout.setRefreshing(true);
//        refreshLayout.setEnabled(false);
    }

    public void setSwipeRefreshLoadedState() {
        ptrLayout.refreshComplete();
    }

    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) mRecyclerView.getLayoutManager();
    }

}
