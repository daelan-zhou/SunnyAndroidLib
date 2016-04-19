package com.ikkong.sunnylibapp.adapter;

import android.widget.AbsListView;

import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibapp.entity.ImageData;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import java.util.Collection;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/4/19
 * Description:
 */
public class ImageDataAdapter extends KJAdapter<ImageData.DataEntity.ListEntity> {

    public ImageDataAdapter(AbsListView view, Collection<ImageData.DataEntity.ListEntity> mDatas) {
        super(view, mDatas, R.layout.list_item);
    }

    @Override
    public void convert(AdapterHolder helper, ImageData.DataEntity.ListEntity item, boolean isScrolling) {
        helper.setText(R.id.tv, item.getPic());
    }
}