/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ikkong.sunnylibrary.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikkong.sunnylibrary.R;

import java.io.File;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class EmptyLayout extends LinearLayout implements
        View.OnClickListener {

    public static final int NETWORK_ERROR = 1; // 网络错误
    public static final int NETWORK_LOADING = 2; // 加载中
    public static final int NODATA = 3; // 没有数据
    public static final int HIDE_LAYOUT = 4; // 隐藏
    public static final String LOADING_IMAGE_FOLDER_NAME = "loading_gif";
    private int mErrorState = NETWORK_LOADING;

    private OnClickListener listener;
    private boolean clickEnable = true;
    private String strNoDataContent = "";

    private TextView tv;
    public GifImageView img;
    private GifDrawable loadingDrawable;

    public EmptyLayout(Context context) {
        super(context);
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = View
                .inflate(getContext(), R.layout.view_error_layout, null);
        img = (GifImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);

        setBackgroundColor(-1);
        setOnClickListener(this);
//        if (getVisibility() == View.GONE) {
            setErrorType(HIDE_LAYOUT);
//        } else {
//            setErrorType(NETWORK_LOADING);
//        }
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
        this.addView(view);
    }

    @Override
    public void onClick(View v) {
        if (clickEnable && listener != null) {
            listener.onClick(v);
        }
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    public void setErrorImag(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (TextUtils.isEmpty(strNoDataContent)) {
            tv.setText("没有数据");
        } else {
            tv.setText(strNoDataContent);
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
        case NETWORK_ERROR:
            mErrorState = NETWORK_ERROR;
            tv.setText("没有网络~");
            img.setImageResource(R.drawable.page_icon_network);
            img.setVisibility(View.VISIBLE);
            clickEnable = true;
            break;
        case NETWORK_LOADING:
            mErrorState = NETWORK_LOADING;
            try {
                AssetManager assetManager = getResources().getAssets();
                String[] loadingList = assetManager.list(LOADING_IMAGE_FOLDER_NAME);
                String imageName = loadingList[((int) (loadingList.length * Math.random()))];
                String absoluteImageName = LOADING_IMAGE_FOLDER_NAME + File.separator + imageName;
                loadingDrawable = new GifDrawable(assetManager, absoluteImageName);
                img.setImageDrawable(loadingDrawable);
            } catch (Exception e) {
            }
            tv.setText("加载中…");
            clickEnable = false;
            break;
        case NODATA:
            mErrorState = NODATA;
            img.setImageResource(R.drawable.page_icon_empty);
            img.setVisibility(View.VISIBLE);
            setTvNoDataContent();
            clickEnable = true;
            break;
        case HIDE_LAYOUT:
            dismiss();
            break;
        default:
            break;
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE) {
            mErrorState = HIDE_LAYOUT;
        }
        super.setVisibility(visibility);
    }
}
