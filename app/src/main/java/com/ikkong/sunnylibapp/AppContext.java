package com.ikkong.sunnylibapp;

import android.app.Application;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/4/21
 * Description:
 */
public class AppContext extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
//        Cube.onCreate(this);
    }
}
