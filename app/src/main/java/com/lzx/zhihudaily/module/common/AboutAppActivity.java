package com.lzx.zhihudaily.module.common;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.base.BaseActivity;

/**
 * Created by lzx on 2016/10/24.
 * 功能：关于App
 */

public class AboutAppActivity extends BaseActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_app;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setTitle("关于App v 1.0");
        mToolbar.setNavigationOnClickListener(view -> finish());
//        //设置StatusBar透明
//        SystemBarHelper.immersiveStatusBar(this);
//        SystemBarHelper.setHeightAndPadding(this, mToolbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initAction() {
        //关于app，评论，我的收藏
        //详情，写评论，主编，主编资料，关于我页被顶上去
    }
}
