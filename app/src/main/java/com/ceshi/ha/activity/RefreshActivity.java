package com.ceshi.ha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ceshi.ha.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class RefreshActivity extends Activity {
    @BindView(R.id.content_web_view)
    public WebView mWebView;
    @BindView(R.id.refresh)
    public SwipeRefreshLayout refresh;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_refresh);
        mWebView = (WebView) findViewById(R.id.content_web_view);
        setSettings();
        mWebView.setWebViewClient(new ContentWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(getRefreshListener());
        mWebView.loadUrl("https://www.baidu.com/");
    }

    private SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        };
        return listener;
    }

    private class ContentWebViewClient extends WebViewClient {
        // 网页加载完成
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (refresh.isRefreshing()) {
                refresh.setRefreshing(false);
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

    }

    private void setSettings() {
        WebSettings s = mWebView.getSettings();
        s.setBuiltInZoomControls(false);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);//支持js
        s.setDefaultTextEncodingName("utf-8");
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/com.game90/databases/");
        s.setPluginState(WebSettings.PluginState.ON);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
    }
}