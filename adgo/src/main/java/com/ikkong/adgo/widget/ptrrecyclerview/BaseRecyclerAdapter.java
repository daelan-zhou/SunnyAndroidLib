package com.ikkong.adgo.widget.ptrrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikkong.adgo.R;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuN;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author:  ikkong ，kymjs
 * Email:   ikkong@163.com
 * Date:    2016/6/24
 * Description:RecyclerView的万能适配器，适配任何一个RecyclerView
 * 2016年12月8日13:16:46 增加支持侧滑菜单
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    protected List<T> realDatas;
    protected final int mItemLayoutId;
    protected Context cxt;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    public BaseRecyclerAdapter(RecyclerView v, Collection<T> datas, int itemLayoutId) {
        if (datas == null) {
            realDatas = new ArrayList<>();
        } else if (datas instanceof List) {
            realDatas = (List<T>) datas;
        } else {
            realDatas = new ArrayList<>(datas);
        }
        mItemLayoutId = itemLayoutId;
        cxt = v.getContext();
    }

    /**
     * Recycler适配器填充方法
     *
     * @param holder viewholder
     * @param item   javabean
     */
    public abstract void convert(RecyclerHolder holder, T item, int position);

    @SuppressWarnings("WrongConstant")
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cxt);
        View contentView = inflater.inflate(mItemLayoutId, parent, false);
        //region 侧滑支持
        if (mSwipeMenuCreator != null) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.yanzhenjie_item_default, parent, false);
            SwipeMenu swipeLeftMenu = new SwipeMenuN(swipeMenuLayout, viewType);
            SwipeMenu swipeRightMenu = new SwipeMenuN(swipeMenuLayout, viewType);
            mSwipeMenuCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);
            int leftMenuCount = swipeLeftMenu.getMenuItems().size();
            if (leftMenuCount > 0) {
                SwipeMenuView swipeLeftMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.swipe_left);
                swipeLeftMenuView.setOrientation(swipeLeftMenu.getOrientation());
                swipeLeftMenuView.bindMenu(swipeLeftMenu, SwipeMenuRecyclerView.LEFT_DIRECTION);
                swipeLeftMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }
            int rightMenuCount = swipeRightMenu.getMenuItems().size();
            if (rightMenuCount > 0) {
                SwipeMenuView swipeRightMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.swipe_right);
                swipeRightMenuView.setOrientation(swipeRightMenu.getOrientation());
                swipeRightMenuView.bindMenu(swipeRightMenu, SwipeMenuRecyclerView.RIGHT_DIRECTION);
                swipeRightMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }
            if (leftMenuCount > 0 || rightMenuCount > 0) {
                ViewGroup viewGroup = (ViewGroup) swipeMenuLayout.findViewById(R.id.swipe_content);
                viewGroup.addView(contentView);
                contentView = swipeMenuLayout;
            }
        }
        //endregion
        return new RecyclerHolder(contentView);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        convert(holder, realDatas.get(position), position);
        //region 侧滑支持
        if (holder.itemView instanceof SwipeMenuLayout) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) holder.itemView;
            int childCount = swipeMenuLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = swipeMenuLayout.getChildAt(i);
                if (childView instanceof SwipeMenuView) {
                    ((SwipeMenuView) childView).bindAdapterViewHolder(holder);
                }
            }
        }
        //endregion
        holder.itemView.setOnClickListener(getOnClickListener(position));
    }

    @Override
    public int getItemCount() {
        return realDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(v, realDatas.get(position), position);
                }
            }
        };
    }

    public BaseRecyclerAdapter<T> refresh(Collection<T> datas) {
        if (realDatas != null && !realDatas.isEmpty()) {
            //去重
            for (T data : datas) {
                if (!realDatas.contains(data)) realDatas.add(data);
            }
        } else {
            if (datas == null) {
                return this;
            } else if (datas instanceof List) {
                realDatas = (List<T>) datas;
            } else {
                realDatas = new ArrayList<>(datas);
            }
        }
        notifyDataSetChanged();
        return this;
    }
    
    
    //region 侧滑支持
    /**
     * Swipe menu creator。
     */
    private SwipeMenuCreator mSwipeMenuCreator;

    /**
     * Swipe menu click listener。
     */
    private OnSwipeMenuItemClickListener mSwipeMenuItemClickListener;

    /**
     * Set to create menu listener.
     *
     * @param swipeMenuCreator listener.
     */
    public void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSwipeMenuCreator = swipeMenuCreator;
    }

    /**
     * Set to click menu listener.
     *
     * @param swipeMenuItemClickListener listener.
     */
    public void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        this.mSwipeMenuItemClickListener = swipeMenuItemClickListener;
    }
    //endregion
    
}
