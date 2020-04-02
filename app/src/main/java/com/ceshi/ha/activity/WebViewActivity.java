package com.ceshi.ha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ceshi.ha.R;
import com.ceshi.ha.databinding.ActivityWebviewBinding;

/**
 * Created by Administrator on 2016/8/10.
 */
public class WebViewActivity extends Activity {

    ActivityWebviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSettings();
        binding.contentWebView.setWebViewClient(new ContentWebViewClient());
        binding.contentWebView.setWebChromeClient(new MyWebChromeClient());

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                binding.contentWebView.reload();
            }
        });
        binding.contentWebView.loadUrl("https://www.jianshu.com/p/8ef6340dc166");
    }

    private class ContentWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (binding.refresh.isRefreshing()) {
                binding.refresh.setRefreshing(false);
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

    }

    private void setSettings() {
        WebSettings s = binding.contentWebView.getSettings();
        s.setBuiltInZoomControls(false);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);//支持js
        s.setDefaultTextEncodingName("utf-8");
        s.setGeolocationEnabled(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
    }
}