package com.ikkong.sunnylibapp.delegate;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibrary.base.delegate.BaseDelegate;


public class MainDelegate extends BaseDelegate {

    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;

    @Override
    public int getRootLayoutId() {
        return R.layout.delegate_main;
    }

    @Override
    public Toolbar getToolbar() {
        mToolbar = get(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("Must include Toolbar and define @+id/toolbar." +
                    " You can get @layout/base_toolbar");
        }
        return mToolbar;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        AppCompatActivity activity = getActivity();
        //设置显示home图标
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //初始化 MaterialMenuDrawable
        final MaterialMenuDrawable materialMenu =
                new MaterialMenuDrawable(activity, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        mDrawerLayout = get(R.id.drawer_layout);
        // 根据侧滑菜单进度控制 MaterialMenuDrawable 旋转进度
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                materialMenu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW, slideOffset);
            }
        });
        // 将 MaterialMenuDrawable 设置为home图标
        mToolbar.setNavigationIcon(materialMenu);
        // 点击home图标,控制侧滑开关
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMenuState();
            }
        });
    }

    public boolean menuIsOpen() {
        return mDrawerLayout.isDrawerOpen(Gravity.LEFT);
    }

    /**
     * 修改侧滑菜单状态
     *
     * @return 修改后菜单的状态
     */
    public boolean changeMenuState() {
        if (mDrawerLayout == null) mDrawerLayout = get(R.id.drawer_layout);
        boolean isOpen = mDrawerLayout.isDrawerOpen(Gravity.LEFT);
        if (isOpen) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
        return !isOpen;
    }
}
