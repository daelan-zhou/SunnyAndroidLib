package com.ikkong.sunnylibrary.interf;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/6/30
 * Description:
 */
public interface OnWebViewImageClickListener {
    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     * @param url 图片地址
     */
    void showImagePreview(String url);

}
