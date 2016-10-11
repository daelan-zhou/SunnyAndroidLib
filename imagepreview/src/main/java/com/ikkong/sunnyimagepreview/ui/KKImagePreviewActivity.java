package com.ikkong.sunnyimagepreview.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.ikkong.sunnyimagepreview.R;
import com.ikkong.sunnyimagepreview.Utils;
import com.ikkong.sunnyimagepreview.download.DownLoadHelper;
import com.ikkong.sunnyimagepreview.download.ImageDownLoadCallBack;

import java.io.File;


/**
 * ================================================
 * 作    者：jeasonlzy,ikkong
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：保留图片预览，去除其他代码
 * ================================================
 */
public class KKImagePreviewActivity extends ImagePreviewBaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mTitleCount.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, imgUrls.length));
            }
        });
        findViewById(R.id.btn_back).setOnClickListener(this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_save) {
                    String url = imgUrls[mCurrentPosition];
                    if(!url.startsWith("http")){
                        Toast.makeText(KKImagePreviewActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    DownLoadHelper.downLoad(KKImagePreviewActivity.this, url, new ImageDownLoadCallBack() {
                        @Override
                        public void onDownLoadSuccess(File file) {
                            final String savePath = Utils.getDownloadImgPath();
                            final boolean success = Utils.copyFile(file,savePath);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(KKImagePreviewActivity.this,success?
                                            ("保存成功:"+savePath):"保存失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                            
                        }

                        @Override
                        public void onDownLoadFailed() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(KKImagePreviewActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                            
                        }
                    });
                    return true;
                }
                return false;
            }
        });
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

    /**
     * 图片预览
     * @param context
     * @param currentPos 当前图片的位置，即第几个
     * @param urls 图片路径，支持网络图片和本地图片
     */
    public static void goPreview(Context context,int currentPos,String... urls){
        Intent intent = new Intent(context, KKImagePreviewActivity.class);
        intent.putExtra(KKImagePreviewActivity.SELECTED_IMAGE_POSITION,currentPos);
        intent.putExtra(KKImagePreviewActivity.EXTRA_IMAGE_URLS,urls);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preview_menu, menu);
        return true;
    }
    
}
