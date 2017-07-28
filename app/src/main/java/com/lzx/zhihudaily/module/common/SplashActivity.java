package com.lzx.zhihudaily.module.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lzx.zhihudaily.R;
import com.lzx.zhihudaily.base.BaseActivity;
import com.lzx.zhihudaily.utils.SnackbarUtil;
import com.lzx.zhihudaily.widget.DailyIconView;


/**
 * Created by lzx on 2016/10/21.
 * 功能：开屏页
 */

public class SplashActivity extends BaseActivity {

    private ImageView splash_image;
    private RelativeLayout splash_bottom_view;
    private int height;
    private DailyIconView mDailyIconView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mDailyIconView = (DailyIconView) findViewById(R.id.daily_icon_view);
        splash_image = (ImageView) findViewById(R.id.splash_image);
        splash_bottom_view = (RelativeLayout) findViewById(R.id.splash_bottom_view);
    }

    @Override
    protected void initToolBar() {
        splash_bottom_view.post(() -> {
            height = splash_bottom_view.getMeasuredHeight();
            splash_bottom_view.setTranslationY(height);
        });
    }

    @Override
    protected void initData() {
//         2017/7/22   由于这个接口已经用不了了，所以直接配个图片就算了，妹子图哦。
//         RetrofitHelper.getZhihuDailyService().getStartImageInfo()
//                 .compose(bindToLifecycle())
//                 .flatMap(new Func1<StartImage, Observable<StartImage>>() {
//                     @Override
//                     public Observable<StartImage> call(StartImage startImage) {
//                         return Observable.just(startImage);
//                     }
//                 })
//                 .subscribeOn(Schedulers.io())
//                 .observeOn(AndroidSchedulers.mainThread())
//                 .subscribe(startImage -> {
//                     initSplashImage(startImage.getImg());
//                 }, throwable -> {
//                     showErrorMsg();
//                 });
        initSplashImage("http://ww4.sinaimg.cn/large/610dc034jw1f41lxgc3x3j20jh0tcn14.jpg");
    }

    private void initSplashImage(String url) {
        Glide.with(SplashActivity.this).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                showErrorMsg();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                splash_image.setImageDrawable(resource);
                startAnimator();
                return true;
            }
        }).into(splash_image);
    }

    private void startAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(splash_bottom_view, "translationY", height, 0);
        animator.setDuration(1000);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startDailyIconAnimator();
            }
        });
    }

    private void startDailyIconAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 270f);
        animator.setTarget(mDailyIconView);
        animator.setDuration(2000).start();
        animator.addUpdateListener(animation1 -> {
            Float value = (Float) animation1.getAnimatedValue();
            mDailyIconView.setSweepAngle(value);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void showErrorMsg() {
        SnackbarUtil.showMessage(splash_image, "出错了，请检查你的网络");
    }

    @Override
    protected void initAction() {

    }
}
