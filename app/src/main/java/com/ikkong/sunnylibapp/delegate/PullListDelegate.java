package com.ikkong.sunnylibapp.delegate;

import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibrary.base.delegate.BaseListDelegate;
import com.ikkong.sunnylibrary.widget.EmptyLayout;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class PullListDelegate extends BaseListDelegate {

    public EmptyLayout mEmptyLayout;

    @Override
    public void initWidget() {
        super.initWidget();
        mEmptyLayout = new EmptyLayout(getActivity());
        setEmptyLayout(mEmptyLayout);
    }

    public void setRefreshHandler(PtrHandler handler) {
        PtrClassicFrameLayout ptr = get(R.id.ptr_layout);
        ptr.setPtrHandler(handler);
    }
}
