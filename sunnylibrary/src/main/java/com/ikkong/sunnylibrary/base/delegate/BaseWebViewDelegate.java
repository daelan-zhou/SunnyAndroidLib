package com.ikkong.sunnylibrary.base.delegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

import com.ikkong.imagepreview.ui.KKImagePreviewActivity;
import com.ikkong.sunnylibrary.R;
import com.ikkong.sunnylibrary.interf.OnWebViewImageClickListener;
import com.ikkong.sunnylibrary.utils.DensityUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/6/23
 * Description:
 */
public class BaseWebViewDelegate extends BaseDelegate {

    protected WebView webView;
    public PtrClassicFrameLayout ptrLayout;
    private ProgressBar progressBar;
     
    @Override
    public int getRootLayoutId() {
        return R.layout.ptr_webview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ptrLayout= get(R.id.ptr_layout);
        progressBar = get(R.id.browser_progress);
        // header
        final MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getActivity().getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtils.dip2px(getActivity(),15), 0, DensityUtils.dip2px(getActivity(),10));
        header.setPtrFrameLayout(ptrLayout);

        ptrLayout.setLoadingMinTime(1000);
        ptrLayout.setDurationToCloseHeader(1500);
        ptrLayout.setHeaderView(header);
        ptrLayout.addPtrUIHandler(header);

        ptrLayout.setDurationToClose(100);
        ptrLayout.setPinContent(true);

        webView = get(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeeClient());
        initWebView(webView);
    }
    
    public void loadUrl(String url){
        webView.loadUrl(url);
    }
    
    public void reload(){
        reload(null);
    }
    
    public void reload(String url){
        if(TextUtils.isEmpty(webView.getUrl())){
            loadUrl(url);
        }else {
            webView.reload();
        }
    }

    public void setSwipeRefreshLoadedState() {
        ptrLayout.refreshComplete();
    }

    public void lazyLoad(final String url){
        ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                reload(url);
            }
        }, 500);
    }

    public void setRefreshHandler(PtrHandler handler) {
        ptrLayout.setPtrHandler(handler);
    }


    @SuppressLint({"SetJavaScriptEnabled"})
    public static void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(15);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
        addWebImageShow(webView.getContext(), webView);
    }

    /**
     * 添加网页的点击图片展示支持
     */
    @JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public static void addWebImageShow(final Context cxt, WebView wv) {
        wv.addJavascriptInterface(new OnWebViewImageClickListener() {
            @Override
            @JavascriptInterface
            public void showImagePreview(String bigImageUrl) {
                if (!TextUtils.isEmpty(bigImageUrl)) {
                    KKImagePreviewActivity.goPreview(cxt,0, bigImageUrl);
                }
            }
        }, "mWebViewImageClickListener");
    }

    /**
     * 设置html中图片支持点击预览
     *
     * @param body html内容
     * @return 修改后的内容
     */
    public static String setImagePreview(String body) {
        // 过滤掉 img标签的width,height属性
        body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
        body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
        // 添加点击图片放大支持
        body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                "$1$2\" onClick=\"mWebViewImageListener.showImagePreview('$2')\"");
        return body;
    }

    private class MyWebChromeeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) { // 进度
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress > 80) {
                setSwipeRefreshLoadedState();
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    }
}
