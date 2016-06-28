package com.ikkong.sunnyimagepreview.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.ikkong.sunnyimagepreview.R;


/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216 ,ikkong
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：保留图片预览，去除其他代码
 * ================================================
 */
public class ImagePreviewActivity extends ImagePreviewBaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mTitleCount.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
            }
        });
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    /** 单击时，隐藏头和尾 */
    @Override
    public void onImageSingleTap() {
        if (topBar.getVisibility() == View.VISIBLE) {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));
            topBar.setVisibility(View.GONE);
            tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
            //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        } else {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_in));
            topBar.setVisibility(View.VISIBLE);
            tintManager.setStatusBarTintResource(R.color.status_bar);//通知栏所需颜色
            //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }
}
