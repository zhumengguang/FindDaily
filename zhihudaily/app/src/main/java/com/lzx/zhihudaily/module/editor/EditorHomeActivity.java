package com.lzx.zhihudaily.module.editor;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.base.BaseActivity;

/**
 * Created by lzx on 2016/10/24.
 * 功能：主编主页
 */

public class EditorHomeActivity extends BaseActivity {

    private Toolbar mToolbar;
    private WebView mWebView;
    private String url;
    private WebViewClientBase webViewClient = new WebViewClientBase();

    @Override
    public int getLayoutId() {
        return R.layout.activity_editor_home;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("主编资料");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setBlockNetworkImage(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);   //api21(5.0系统) 支持base64位图片
        }
        mWebView.setWebViewClient(webViewClient);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.loadUrl(url);
    }

    public class WebViewClientBase extends WebViewClient {
        
    }

    @Override
    protected void initAction() {

    }
}
