package com.ikkong.sunnylibrary.base;

import com.kymjs.frame.presenter.FragmentPresenter;
import com.kymjs.frame.view.IDelegate;

/**
 * 主界面内容Fragment
 *
 * @author kymjs (http://www.kymjs.com/) on 11/27/15.
 */
public abstract class BaseMainFragment<T extends IDelegate> extends FragmentPresenter<T> {

    public void onShow() {
    }
}
