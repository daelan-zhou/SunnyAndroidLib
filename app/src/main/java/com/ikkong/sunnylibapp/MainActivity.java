package com.ikkong.sunnylibapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ikkong.sunnylibapp.adapter.ImageDataAdapter;
import com.ikkong.sunnylibapp.entity.ImageData;
import com.ikkong.sunnylibrary.widget.EmptyLayout;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.DensityUtils;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;


public class MainActivity extends KJActivity {

    @BindView(id = R.id.empty_layout, click = true)
    private EmptyLayout mEmptyLayout;
    @BindView(id = R.id.list_view)
    private ListView mListView;
    @BindView(id = R.id.ptr_layout)
    private PtrClassicFrameLayout ptrLayout;
    private HttpConfig config;
    private KJHttp kjh;
    private HttpParams params;

    private int pageCount = 1, pageIndex;
    private ImageDataAdapter adapter;
    private ArrayList<ImageData.DataEntity.ListEntity> list = new ArrayList<>();

    private String url = "http://cube-server.liaohuqiu.net/api_demo/image-list.php";

    @Override
    public void setRootView() {
        setContentView(R.layout.meterial_ptr_listview);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptrLayout.autoRefresh();
            }
        });
        mListView.setDivider(new ColorDrawable(Color.argb(0, 0, 0, 0)));
        mListView.setSelector(new ColorDrawable(Color.argb(0, 0, 0, 0)));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewInject.toast("click position " + position);
            }
        });
        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtils.dip2px(this,15), 0, DensityUtils.dip2px(this,10));
        header.setPtrFrameLayout(ptrLayout);

        ptrLayout.setLoadingMinTime(1000);
        ptrLayout.setDurationToCloseHeader(1500);
        ptrLayout.setHeaderView(header);
        ptrLayout.addPtrUIHandler(header);
        ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrLayout.autoRefresh(false);
            }
        }, 100);
        ptrLayout.setDurationToClose(100);
        ptrLayout.setPinContent(true);
        
        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }
        });
    }

    public void updateData() {
        config = new HttpConfig();
        kjh = new KJHttp(config);
        params = new HttpParams();
        kjh.get(url, params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                ImageData data = ImageData.objectFromData(t);
                if (data != null) {
                    list.clear();
                    list.addAll(data.getData().getList());
                    if (adapter == null) {
                        adapter = new ImageDataAdapter(mListView, list);
                        mListView.setAdapter(adapter);
                    } else {
                        adapter.refresh(list);
                    }
                    mEmptyLayout.dismiss();
                } else {
                    mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                if (adapter != null && adapter.getCount() > 0) {
                    return;
                } else {
                    mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                ptrLayout.refreshComplete();
            }
        });
    }
}
