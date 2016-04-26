package com.ikkong.sunnylibapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibapp.api.ApiHelper;
import com.ikkong.sunnylibapp.entity.ResponseJoke;
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
 * Date:    2016/4/25
 * Description:
 */
public class JokeListFragment extends BaseListFragment<ResponseJoke.ResultEntity.DataEntity>{
    private Subscription cacheSubscript;
    private int type;
    public static final String TYPE_KEY = "tag_key";
    public static final int TYPE_JOKEC = 0;
    public static final int TYPE_JOKEI = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        type = getArguments().getInt(TYPE_KEY);
        Loger.debug("type = "+ type);
        super.onViewCreated(view, savedInstanceState);
        //不做缓存没必要执行
        cacheSubscript = Observable.just(RxVolley.getCache(type==TYPE_JOKEC?ApiHelper.JOKEC:ApiHelper.JOKEI))
                .filter(new Func1<byte[], Boolean>() {
                    @Override
                    public Boolean call(byte[] cache) {
                        return cache != null && cache.length != 0;
                    }
                })
                .map(new Func1<byte[], ArrayList<ResponseJoke.ResultEntity.DataEntity>>() {
                    @Override
                    public ArrayList<ResponseJoke.ResultEntity.DataEntity> call(byte[] bytes) {
                        return parserInAsync(bytes);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<ResponseJoke.ResultEntity.DataEntity>>() {
                    @Override
                    public void call(ArrayList<ResponseJoke.ResultEntity.DataEntity> list) {
                        datas = list;
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
    protected BasePullUpRecyclerAdapter<ResponseJoke.ResultEntity.DataEntity> getAdapter() {
        return new BasePullUpRecyclerAdapter<ResponseJoke.ResultEntity.DataEntity>(recyclerView, datas, R.layout.list_item_wxhot_card) {

            @Override
            public void convert(RecyclerHolder holder, final ResponseJoke.ResultEntity.DataEntity item, int position) {
                holder.setText(R.id.tv_content, item.getContent());
                ImageView imageView = holder.getView(R.id.iv_img);
                String imageUrl = item.getUrl();
                if (TextUtils.isEmpty(imageUrl)) {
                    imageView.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    Glide
                            .with(JokeListFragment.this)
                            .load(imageUrl)
                            .centerCrop()
                            .placeholder(R.mipmap.thum)
                            .crossFade()
                            .into(new GlideDrawableImageViewTarget(imageView, 1));
                }
            }
        };
    }

    @Override
    protected ArrayList<ResponseJoke.ResultEntity.DataEntity> parserInAsync(byte[] t) {
        return (ArrayList<ResponseJoke.ResultEntity.DataEntity>) ResponseJoke.objectFromData(new String(t)).getResult().getData();
    }

    @Override
    public void onBottom() {
        if(datas.size()%pageSize == 0){
            doRequest();
            adapter.setState(BasePullUpRecyclerAdapter.STATE_LOADING);
        }else{
            adapter.setState(BasePullUpRecyclerAdapter.STATE_NO_MORE);
        }
    }

    @Override
    public void doRequest() {
        HttpParams params = new HttpParams();
        params.put("key","1a99bc6be2bfa7a94d5d4da58c32df77");
        params.put("page",pageIndex);
        params.put("pagesize",pageSize);
        new RxVolley.Builder().url(type==TYPE_JOKEC?ApiHelper.JOKEC:ApiHelper.JOKEI)
                .contentType(RxVolley.Method.GET)
                .params(params)
                .cacheTime(1)
                .callback(callBack)
                .doTask();
        pageIndex++;
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
