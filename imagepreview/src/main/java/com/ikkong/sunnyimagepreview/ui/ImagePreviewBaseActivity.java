package com.ikkong.sunnyimagepreview.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikkong.sunnyimagepreview.R;
import com.ikkong.sunnyimagepreview.Utils;
import com.ikkong.sunnyimagepreview.adapter.ImagePageAdapter;
import com.ikkong.sunnyimagepreview.view.ViewPagerFixed;

/**
 * ================================================
 * 作    者：ikkong,jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：图片预览的基类,保留图片预览，去除其他代码
 * ================================================
 */
public abstract class ImagePreviewBaseActivity extends ImageBaseActivity {
    public static final String SELECTED_IMAGE_POSITION = "SELECTED_IMAGE_POSITION";
    public static final String EXTRA_IMAGE_URLS = "EXTRA_IMAGE_URLS";

    protected String[] imgUrls;                     //跳转进ImagePreviewFragment的图片
    protected int mCurrentPosition = 0;              //跳转进ImagePreviewFragment时的序号，第几个图片
    protected TextView mTitleCount;                  //显示当前图片的位置  例如  5/31
    protected View content;
    protected View topBar;
    protected ViewPagerFixed mViewPager;
    protected ImagePageAdapter mAdapter;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        toolbar = (Toolbar) findViewById(R.id.top_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mCurrentPosition = getIntent().getIntExtra(SELECTED_IMAGE_POSITION, 0);
        imgUrls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);

        //初始化控件
        content = findViewById(R.id.content);

        //因为状态栏透明后，布局整体会上移，所以给头部加上状态栏的margin值，保证头部不会被覆盖
        topBar = findViewById(R.id.top_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) topBar.getLayoutParams();
            params.topMargin = Utils.getStatusHeight(this);
            topBar.setLayoutParams(params);
        }

        mTitleCount = (TextView) findViewById(R.id.tv_des);

        mViewPager = (ViewPagerFixed) findViewById(R.id.viewpager);
        mAdapter = new ImagePageAdapter(this, imgUrls);
        mAdapter.setPhotoViewClickListener(new ImagePageAdapter.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1) {
                onImageSingleTap();
            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPosition, false);

        //初始化当前页面的状态
        mTitleCount.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, imgUrls.length));
    }

    /** 单击时，隐藏头和尾 */
    public abstract void onImageSingleTap();
}