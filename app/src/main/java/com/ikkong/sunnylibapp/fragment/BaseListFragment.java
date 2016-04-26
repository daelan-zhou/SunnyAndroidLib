package com.ikkong.sunnylibapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibapp.delegate.PullListDelegate;
import com.ikkong.sunnylibrary.adapter.BasePullUpRecyclerAdapter;
import com.ikkong.sunnylibrary.adapter.BaseRecyclerAdapter;
import com.ikkong.sunnylibrary.base.BaseMainFragment;
import com.ikkong.sunnylibrary.interf.IRequestVo;
import com.ikkong.sunnylibrary.widget.EmptyLayout;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.toolbox.Loger;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 下拉列表界面基类
 *  实现懒加载 2016/4/25
 */
public abstract class BaseListFragment<T> extends BaseMainFragment<PullListDelegate> implements
        PtrHandler, IRequestVo, BaseRecyclerAdapter.OnItemClickListener {
    private final String TAG = getClass().getSimpleName();
    protected BasePullUpRecyclerAdapter<T> adapter;
    protected RecyclerView recyclerView;
    protected ArrayList<T> datas = new ArrayList<>();
    protected int pageIndex=1,//第几页
            pageCount,  //总页数
            pageSize = 5;//每页数量

    private boolean isPrepared;//懒加载 判断是否初始化完成
    private boolean isFirstVisible = true;//首次可见

    protected abstract BasePullUpRecyclerAdapter<T> getAdapter();

    protected abstract ArrayList<T> parserInAsync(byte[] t);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onVisible();
            }
        }
    }
    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }
    /**
     * 当 fragment 首次可见, 懒加载数据
     */
    protected void onFirstUserVisible(){viewDelegate.lazyLoad();}
    protected void onVisible(){}

    @Override
    public void onShow() {
        setUserVisibleHint(true);
    }

    protected HttpCallback callBack = new HttpCallback() {
        private ArrayList<T> tempDatas;

        @Override
        public void onSuccessInAsync(byte[] t) {
            super.onSuccessInAsync(t);
            try {
                tempDatas = parserInAsync(t);
            } catch (Exception e) {
                tempDatas = null;
            }
        }

        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            if (viewDelegate != null && viewDelegate.mEmptyLayout != null) {
                if (tempDatas == null || tempDatas.isEmpty() || adapter == null || adapter
                        .getItemCount() < 1) {
                    viewDelegate.mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                } else {
                    viewDelegate.mEmptyLayout.dismiss();
                    datas.addAll(tempDatas);
                    adapter.refresh(datas);
                    adapter.setState(BasePullUpRecyclerAdapter.STATE_INVISIBLE);
                }
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            Loger.debug("====网络请求异常" + strMsg);
            //有可能界面已经关闭网络请求仍然返回
            if (viewDelegate != null && viewDelegate.mEmptyLayout != null && adapter != null) {
                if (adapter.getItemCount() > 1) {
                    viewDelegate.mEmptyLayout.dismiss();
                } else {
                    viewDelegate.mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                }
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (viewDelegate != null) {
                viewDelegate.setSwipeRefreshLoadedState();
            }
        }
    };

    @Override
    protected Class<PullListDelegate> getDelegateClass() {
        return PullListDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        recyclerView = viewDelegate.get(R.id.recyler_view);
        adapter = getAdapter();
        bindEven();
        viewDelegate.setRefreshHandler(this);
        adapter.setOnItemClickListener(this);
//        doRequest();//使用懒加载，此处就不请求数据了
    }

    private void bindEven() {
        viewDelegate.mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.clear();
                pageIndex = 1;
                doRequest();
                viewDelegate.mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //滚动到底部的监听
                    LinearLayoutManager layoutManager = viewDelegate.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastItems = layoutManager.findFirstVisibleItemPosition();
                    if ((pastItems + visibleItemCount) >= totalItemCount) {
                        onBottom();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void onBottom() {
        adapter.setState(BasePullUpRecyclerAdapter.STATE_NO_MORE);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        datas.clear();
        pageIndex = 1;
        doRequest();
    }
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }
}
