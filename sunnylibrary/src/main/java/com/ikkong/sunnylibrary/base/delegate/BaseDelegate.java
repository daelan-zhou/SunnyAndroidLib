package com.ikkong.sunnylibrary.base.delegate;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import com.kymjs.frame.view.AppDelegate;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/4/26
 * Description:
 */
public abstract class BaseDelegate extends AppDelegate {

    @Override
    public void toast(CharSequence msg) {
        if (!TextUtils.isEmpty(msg)) {
            Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).show();
        }
    }
}
