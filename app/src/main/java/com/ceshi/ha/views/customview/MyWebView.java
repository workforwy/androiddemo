package com.ceshi.ha.views.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ceshi.ha.utils.NetWork;

/**
 * Created by WY on 2016/11/25 0025.
 */

public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
        init(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context mContext) {
        WebSettings s = getSettings();
        s.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //是否需要用户手势来播放Media，默认true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            s.setMediaPlaybackRequiresUserGesture(false);
        }
        //是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        s.setBuiltInZoomControls(false);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //是否支持ViewPort的meta
        s.setUseWideViewPort(true);
        //是否保存表单数据，默认false
        s.setSaveFormData(true);
        s.setGeolocationEnabled(true);
        //支持js
        s.setJavaScriptEnabled(true);
        // 开启Application Cache功能
        s.setAppCacheEnabled(true);
        //设置 缓存模式
        if (NetWork.isNetworkConnected(mContext)) {
            s.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            s.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启 DOM storage API 功能
        s.setDomStorageEnabled(true);
        //js开启窗口命令
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        //是否允许访问WebView内部文件，默认true
        s.setAllowFileAccess(true);
        setWebChromeClient(new MyWebChromeClient());
        setWebViewClient(new MyWebViewClient());
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }

    class MyWebChromeClient extends WebChromeClient {

    }
}
