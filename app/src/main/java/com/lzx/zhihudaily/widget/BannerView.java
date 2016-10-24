package com.lzx.zhihudaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzx.zhihudaily.R;

/**
 * Created by lzx on 2016/10/21.
 * 功能：
 */

public class BannerView extends LinearLayout {

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView banner_image;
    private TextView banner_title;

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_banner_view, BannerView.this);
        initView();
    }

    private void initView() {
        banner_image = (ImageView) findViewById(R.id.banner_image);
        banner_title = (TextView) findViewById(R.id.banner_title);
    }

    public void setImageUrl(String url) {
        Glide.with(getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(banner_image);
    }

    public void setBannerTitle(String title) {
        banner_title.setText(title);
    }

}
