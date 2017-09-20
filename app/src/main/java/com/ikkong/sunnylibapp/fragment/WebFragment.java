package com.ikkong.sunnylibapp.fragment;

import android.view.View;

import com.ikkong.sunnylibrary.base.BaseMainFragment;
import com.ikkong.sunnylibrary.base.delegate.BaseWebViewDelegate;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/6/23
 * Description:
 */
public class WebFragment extends BaseMainFragment<BaseWebViewDelegate> implements PtrHandler{
    @Override
    protected Class<BaseWebViewDelegate> getDelegateClass() {
        return BaseWebViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setRefreshHandler(this);
    }

    @Override
    public void onShow() {
        super.onShow();
        viewDelegate.lazyLoad("http://www.baidu.com");
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        viewDelegate.reload();
    }
    
    
}
