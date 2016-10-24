package com.lzx.zhihudaily.module.comment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.adapter.CommentPagerAdapter;
import com.lzx.zhihudaily.base.BaseActivity;
import com.lzx.zhihudaily.eitity.StoryExtra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xian on 2016/10/23.
 */

public class CommentActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private StoryExtra mStoryExtra;
    private String newsId;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private CommonListFragment mLongCommentFragment, mShortCommentFragment;
    private CommentPagerAdapter mPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle("评论");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_comment_write) {
            Intent intent = new Intent(CommentActivity.this, WriteCommentActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        mStoryExtra = getIntent().getParcelableExtra("StoryExtra");
        newsId = getIntent().getStringExtra("newsId");

        titles.add("长评论" + " (" + mStoryExtra.getLong_comments() + ")");
        titles.add("短评论" + " (" + mStoryExtra.getShort_comments() + ")");
        mLongCommentFragment = CommonListFragment.newInstance(newsId, "long_comment");
        mShortCommentFragment = CommonListFragment.newInstance(newsId, "short_comment");
        fragmentList.add(mLongCommentFragment);
        fragmentList.add(mShortCommentFragment);
        mPagerAdapter = new CommentPagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initAction() {

    }


}
