package com.pupu.rushui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.util.Logger;

/**
 * Created by pupu on 2018/4/3.
 */

public class MinuteDiskView extends DiskView {
    private static final String TAG = MinuteDiskView.class.getName();
    Paint mPaint;
    int textHeight;
    int minute;//当前分钟数

    public MinuteDiskView(Context context) {
        this(context, null);
    }

    public MinuteDiskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MinuteDiskView(Context context, int radius) {
        super(context);
        mRadius = radius;
        initView(context);
    }

    private void initView(Context context) {
        if (mRadius == 0) {
            mRadius = 600;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xff2c3479);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(CommonUtil.sp2px(mContext, 20));//默认20sp
    }

    private void initView(Context context, AttributeSet attrs) {
        if (mRadius == 0) {
            mRadius = 600;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xff2c3479);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(CommonUtil.sp2px(mContext, 20));//默认20sp
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆盘
        mPaint.setColor(0xff2c3479);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        //画数字
        mPaint.setColor(0xffffffff);
        Rect bounds = new Rect();
        for (int i = 0; i < 60; i++) {
            if (i == minute) {
                mPaint.setColor(0xfff43e3e);
            } else {
                mPaint.setColor(0xffffffff);
            }
            if (i % 10 != 0) {
                if (i % 5 == 0) {
                    canvas.drawCircle(mRadius, 2 * mRadius - textHeight * 3 / 2, CommonUtil.sp2px(mContext, 20) / 5, mPaint);
                } else {
                    canvas.drawCircle(mRadius, 2 * mRadius - textHeight * 3 / 2, CommonUtil.sp2px(mContext, 20) / 8, mPaint);
                }
            } else {
                mPaint.getTextBounds(i + "", 0, (i + "").length(), bounds);
                textHeight = bounds.height();
                canvas.drawText(i + "", mRadius - bounds.width() / 2, mRadius * 2 - bounds.height(), mPaint);
            }
            canvas.rotate(-6, mRadius, mRadius);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                if (isNeedReturn == false) {
                    Logger.i(TAG, "degree + curDegree - startDegree==>" + (degree + curDegree - startDegree));
                    int tmpDegree = degree + curDegree - startDegree;
                    int tmpD = tmpDegree % 6;
                    int detaD = tmpDegree - tmpD;
                    Logger.i(TAG, "detaD==>" + detaD);
                    if (detaD < 0) {
                        detaD = 360 + detaD;
                    }
                    if (tmpD == 0) {
                        minute = detaD / 6;
                    } else if (tmpD < 3) {
                        minute = detaD / 6;
                    } else {
                        minute = detaD / 6 + 1;
                    }
                    if (onMinuteChangedListener != null) {
                        onMinuteChangedListener.onMinuteChanged(minute);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedReturn == false) {
                    //做定位矫正,定位到最近的数字
                    int tmp = degree % 6;
                    int detaDD = degree - tmp;
                    if (detaDD < 0) {
                        detaDD = 360 + detaDD;
                    }
                    if (tmp == 0) {
                        minute = detaDD / 6;
                    } else if (tmp < 3) {
                        minute = detaDD / 6;
                        animator = ValueAnimator.ofInt(degree, degree - tmp);
                        animator.setDuration(100);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                degree = (int) animation.getAnimatedValue();
                                postInvalidate();
                            }
                        });
                        animator.start();
                    } else {
                        minute = detaDD / 6 + 1;
                        animator = ValueAnimator.ofInt(degree, degree - tmp + 6);
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
                    if (onMinuteChangedListener != null) {
                        onMinuteChangedListener.onMinuteChanged(minute);
                    }
                }
                break;
        }
        if (Math.sqrt(
                (startX - mRadius) * (startX - mRadius) + (startY - mRadius) * (startY - mRadius)
        ) > mRadius) {
            return false;
        }
        return true;
    }

    /**
     * 显示当前的秒数
     */
    public void setCurTime(int minute) {
        //换算成对应的角度
        this.minute = minute;
        int tmpDegree = 6 * minute;
        if (tmpDegree == degree) {
            return;
        }
        animator = ValueAnimator.ofInt(degree, tmpDegree);
        animator.setInterpolator(new DecelerateInterpolator());
        if (Math.abs(tmpDegree - degree) / 6 <= 20) {
            animator.setDuration(40 * Math.abs(tmpDegree - degree) / 6);
        } else {
            animator.setDuration(800);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    OnMinuteChangedListener onMinuteChangedListener;

    public void setOnMinuteChangedListener(OnMinuteChangedListener listener) {
        this.onMinuteChangedListener = listener;
    }

    interface OnMinuteChangedListener {
        void onMinuteChanged(int minute);
    }
}
