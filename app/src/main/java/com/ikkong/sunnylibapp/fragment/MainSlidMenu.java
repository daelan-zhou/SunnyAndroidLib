package com.ikkong.sunnylibapp.fragment;

import android.os.Bundle;
import android.view.View;

import com.ikkong.sunnylibapp.MainActivity;
import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibapp.delegate.MainSlidMenuDelegate;
import com.ikkong.sunnylibrary.model.Event;
import com.kymjs.frame.presenter.FragmentPresenter;
import com.kymjs.rxvolley.rx.RxBus;
import com.kymjs.rxvolley.toolbox.Loger;


/**
 * 侧滑界面逻辑代码
 *
 */
public class MainSlidMenu extends FragmentPresenter<MainSlidMenuDelegate> implements View
        .OnClickListener {

    @Override
    protected Class<MainSlidMenuDelegate> getDelegateClass() {
        return MainSlidMenuDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this,
                R.id.menu_item_tag1,
                R.id.menu_item_tag2,
                R.id.menu_item_tag3,
                R.id.menu_item_tag4
                );
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Loger.debug("=====MainSlidMenu=onClick====");
        Event event = new Event();
        event.setAction(MainActivity.MENU_CLICK_EVEN);
        event.setObject(v);
        RxBus.getDefault().post(event);
    }
}
