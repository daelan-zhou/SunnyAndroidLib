package com.ikkong.adgo.widget.smrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.ikkong.adgo.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/12/8
 * Description:默认的右侧侧滑菜单 编辑+删除
 */

public class DefaultSwipeMenuCreator implements SwipeMenuCreator {
    private Context context;
    private int type;//0 编辑、删除 1编辑

    public DefaultSwipeMenuCreator(Context context) {
        this.context = context;
    }

    public DefaultSwipeMenuCreator(Context context,int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
        int width = context.getResources().getDimensionPixelSize(R.dimen.size_64);
        if(type == 1){
            width = context.getResources().getDimensionPixelSize(R.dimen.size_100);
        }
        // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        SwipeMenuItem addItem = new SwipeMenuItem(context)
                .setBackgroundDrawable(R.drawable.adgo_selector_yellow)
                .setText("编辑")
                .setTextColor(Color.WHITE)
                .setWidth(width)
                .setHeight(height);
        swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
        if(type == 1){
            //type=1 没有删除
            return;
        }
        SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                .setBackgroundDrawable(R.drawable.adgo_selector_red)
                .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                .setTextColor(Color.WHITE)
                .setWidth(width)
                .setHeight(height);
        swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
    }
}
