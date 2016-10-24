package com.lzx.zhihudaily.module.collect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.CommentPagerAdapter;
import com.lzx.zhihudaily.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xian on 2016/10/23.
 */

public class CollectActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private ArticleCollectFragment mArticleCollectFragment;
    private ThemeDailyCollectFragment mThemeDailyCollectFragment;
    private CommentPagerAdapter mPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle("我的收藏");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {
        titles.add("文章");
        titles.add("主题日报");
        mArticleCollectFragment = ArticleCollectFragment.newInstance();
        mThemeDailyCollectFragment = ThemeDailyCollectFragment.newInstance();
        fragmentList.add(mArticleCollectFragment);
        fragmentList.add(mThemeDailyCollectFragment);
        mPagerAdapter = new CommentPagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initAction() {

    }
}
