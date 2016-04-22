package com.ikkong.sunnylibapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibapp.api.ApiHelper;
import com.ikkong.sunnylibapp.entity.ResponseHotwxListEntity;
import com.ikkong.sunnylibrary.adapter.BasePullUpRecyclerAdapter;
import com.ikkong.sunnylibrary.adapter.RecyclerHolder;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.toolbox.Loger;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/4/21
 * Description:
 */
public class HotwxListFragment extends BaseListFragment<ResponseHotwxListEntity.ResultEntity.ListEntity>{
//    ImageLoader imageLoader;
    private Subscription cacheSubscript;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        imageLoader = ImageLoaderFactory.create(getActivity());
        //不做缓存没必要执行
        cacheSubscript = Observable.just(RxVolley.getCache(ApiHelper.HOTWX))
                .filter(new Func1<byte[], Boolean>() {
                    @Override
                    public Boolean call(byte[] cache) {
                        return cache != null && cache.length != 0;
                    }
                })
                .map(new Func1<byte[], ArrayList<ResponseHotwxListEntity.ResultEntity.ListEntity>>() {
                    @Override
                    public ArrayList<ResponseHotwxListEntity.ResultEntity.ListEntity> call(byte[] bytes) {
                        return parserInAsync(bytes);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<ResponseHotwxListEntity.ResultEntity.ListEntity>>() {
                    @Override
                    public void call(ArrayList<ResponseHotwxListEntity.ResultEntity.ListEntity> blogs) {
                        datas = blogs;
                        adapter.refresh(datas);
                        viewDelegate.mEmptyLayout.dismiss();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    @Override
    protected BasePullUpRecyclerAdapter<ResponseHotwxListEntity.ResultEntity.ListEntity> getAdapter() {
        return new BasePullUpRecyclerAdapter<ResponseHotwxListEntity.ResultEntity.ListEntity>(recyclerView, datas, R.layout.list_item_wxhot_card) {
//            final View.OnClickListener imageClickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    KJGalleryActivity.toGallery(getActivity(), (String) v.getTag());
//                }
//            };

            @Override
            public void convert(RecyclerHolder holder, final ResponseHotwxListEntity.ResultEntity.ListEntity item, int position) {
                holder.setText(R.id.tv_content, item.getTitle());
                ImageView imageView = holder.getView(R.id.iv_img);
                String imageUrl = item.getFirstImg().trim();
                if (TextUtils.isEmpty(imageUrl)) {
                    imageView.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
//                    imageView.loadImage(imageLoader, imageUrl);
                    Glide
                            .with(HotwxListFragment.this)
                            .load(imageUrl)
                            .centerCrop()
                            .placeholder(R.mipmap.thum)
                            .crossFade()
                            .into(imageView);
                    //在列表点击图片就直接进详情了,没必要进图片预览(布局文件中已取消焦点)
                }
            }
        };
    }

    @Override
    protected ArrayList<ResponseHotwxListEntity.ResultEntity.ListEntity> parserInAsync(byte[] t) {
        return (ArrayList<ResponseHotwxListEntity.ResultEntity.ListEntity>) ResponseHotwxListEntity.objectFromData(new String(t)).getResult().getList();
    }

    @Override
    public void onBottom() {
        Loger.debug("===onBottom===datas.size():"+datas.size());
        if(datas.size()%pageSize == 0){
            doRequest();
            pageIndex++;
            adapter.setState(BasePullUpRecyclerAdapter.STATE_LOADING);
        }else{
            adapter.setState(BasePullUpRecyclerAdapter.STATE_NO_MORE);
        }
    }

    @Override
    public void doRequest() {
        HttpParams params = new HttpParams();
        params.put("key","f647bc3c3cfc2b4d1efc9b75a8282fbe");
        params.put("pno",pageIndex);
        params.put("ps",pageSize);
        new RxVolley.Builder().url(ApiHelper.HOTWX)
                .contentType(RxVolley.Method.GET)
                .params(params)
                .cacheTime(1)
                .callback(callBack)
                .doTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cacheSubscript != null && cacheSubscript.isUnsubscribed())
            cacheSubscript.unsubscribe();
    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }
}
