package com.pupu.rushui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.pupu.rushui.util.CommonUtil;

/**
 * Created by pupu on 2018/4/3.
 */

public class SecondDiskView extends DiskView {
    Paint mPaint;

    public SecondDiskView(Context context) {
        this(context, null);
    }

    public SecondDiskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SecondDiskView(Context context, int radius) {
        super(context);
        mRadius = radius;
        initView(context);
    }

    private void initView(Context context) {
        if (mRadius == 0) {
            mRadius = 650;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffffffff);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(CommonUtil.sp2px(mContext, 20));//默认20sp
    }

    private void initView(Context context, AttributeSet attrs) {
        if (mRadius == 0) {
            mRadius = 650;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffffffff);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(CommonUtil.sp2px(mContext, 20));//默认20sp
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆点
        mPaint.setColor(0xffffffff);
        for (int i = 0; i < 60; i++) {
            if (i == 0) {
                mPaint.setColor(0xfff0ff00);
                canvas.drawCircle(mRadius, 2 * mRadius, CommonUtil.sp2px(mContext, 20) / 5, mPaint);
                mPaint.setColor(0xffffffff);
            } else {
                canvas.drawCircle(mRadius, 2 * mRadius, CommonUtil.sp2px(mContext, 20) / 5, mPaint);
            }
            canvas.rotate(-6, mRadius, mRadius);
        }
    }

    /**
     * 显示当前的秒数
     */
    public void setCurTime(int second) {
        //换算成对应的角度
        int tmpDegree = 6 * (second - 1);
        if (tmpDegree == degree) {
            return;
        }
        animator = ValueAnimator.ofInt(degree, tmpDegree);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
