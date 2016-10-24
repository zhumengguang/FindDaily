package com.lzx.zhihudaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by xian on 2016/10/22.
 */

public class ParallaxScrollView extends ScrollView {

    private ScrollviewListener scrollViewListener = null;

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context) {
        super(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }

    public void setScrollViewListener(ScrollviewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface ScrollviewListener {
        void onScrollChanged(ParallaxScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
