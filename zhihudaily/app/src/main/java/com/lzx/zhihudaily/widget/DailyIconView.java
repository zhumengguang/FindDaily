package com.lzx.zhihudaily.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lzx on 2016/10/24.
 * 功能：开屏页的图标
 */

public class DailyIconView extends View {

    private Paint mPaint;
    private int mStrokeWidth;
    private float sweepAngle = 0f;

    public DailyIconView(Context context) {
        super(context);
        init();
    }

    public DailyIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DailyIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();
    }

    private void init() {
        mStrokeWidth = 10;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        RectF mOval = new RectF(mStrokeWidth + paddingLeft + paddingRight, mStrokeWidth + paddingTop + paddingBottom, width - mStrokeWidth, height - mStrokeWidth);
        canvas.drawArc(mOval, 90, sweepAngle, false, mPaint);
    }
}
