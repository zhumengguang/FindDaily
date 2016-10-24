package com.lzx.zhihudaily.module.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.base.BaseActivity;

/**
 * Created by xian on 2016/10/23.
 */

public class AboutMeActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("关于我");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initAction() {

    }
}
