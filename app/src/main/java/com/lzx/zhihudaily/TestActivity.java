package com.lzx.zhihudaily;

import android.animation.ValueAnimator;
import android.os.Bundle;

import com.lzx.zhihudaily.base.BaseActivity;
import com.lzx.zhihudaily.widget.DailyIconView;

/**
 * Created by lzx on 2016/10/24.
 * 功能：
 */

public class TestActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    private DailyIconView mDailyIconView;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mDailyIconView = (DailyIconView) findViewById(R.id.daily_icon_view);
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initData() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 270f);
        animator.setTarget(mDailyIconView);
        animator.setDuration(5000).start();
        animator.addUpdateListener(animation -> {
            Float value = (Float) animation.getAnimatedValue();
            mDailyIconView.setSweepAngle(value);
        });
    }

    @Override
    protected void initAction() {

    }
}
