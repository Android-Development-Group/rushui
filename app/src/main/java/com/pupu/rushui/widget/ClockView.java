package com.pupu.rushui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pupu.rushui.R;

/**
 * Created by pupu on 2018/3/25.
 */

public class ClockView extends View {

    Context mContext;
    int width, height;
    int centerX, centerY;//中心点坐标
    int minuteThickness, minuteLength;//分针厚度和长度
    Paint minutePaint;//分针画笔
    int hourThickness, hourLength;//时针厚度、长度
    Paint hourPaint;
    int secondThickness, secondLength;//秒针厚度、长度
    Paint secondPaint;

    int degree = 95;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        width = a.getLayoutDimension(R.styleable.ClockView_android_layout_width, 560);
        height = a.getLayoutDimension(R.styleable.ClockView_android_layout_height, 560);
        centerX = width / 2;
        centerY = height / 2;

        minuteLength = width / 2 - 112;
        minuteThickness = 12;

        hourLength = minuteLength - 56;
        hourThickness = 16;

        secondLength = minuteLength + 24;
        secondThickness = 4;

        //初始化相关画笔
        minutePaint = new Paint();
        minutePaint.setStrokeWidth(4);
        minutePaint.setColor(0xff7e7e7e);
//        minutePaint.setShadowLayer(12, 0, 4, Color.GRAY);

        hourPaint = new Paint();
        hourPaint.setStrokeWidth(4);
        hourPaint.setColor(0xff7e7e7e);
//        hourPaint.setShadowLayer(12, 0, 2, Color.GRAY);

        secondPaint = new Paint();
        secondPaint.setColor(0xffe54747);
        secondPaint.setAntiAlias(true);

        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (degree == 360) {
            degree = 0;
        }

        //画背景
        Bitmap clockBkg = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.img_clock_bg);
        canvas.drawBitmap(clockBkg, null,
                new Rect(0, 0, width, height), null);

        //画时针
//        hourPaint.setColor(0xff747474);
//        hourPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawRoundRect(new RectF(centerX - hourThickness / 2, centerY - hourLength, centerX + hourThickness / 2,
//                centerY + hourThickness / 2), hourThickness, hourThickness, hourPaint);
//        hourPaint.setColor(0xff474747);
//        hourPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRoundRect(new RectF(centerX - hourThickness / 2, centerY - hourLength, centerX + hourThickness / 2,
//                centerY + hourThickness / 2), hourThickness, hourThickness, hourPaint);

        //画分针
//        minutePaint.setColor(0xff747474);
//        minutePaint.setStyle(Paint.Style.FILL);
//        canvas.drawRoundRect(new RectF(centerX - minuteThickness / 2, centerY - minuteLength, centerX + minuteThickness / 2,
//                centerY + minuteThickness / 2), minuteThickness, minuteThickness, minutePaint);
//        minutePaint.setColor(0xff474747);
//        minutePaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRoundRect(new RectF(centerX - minuteThickness / 2, centerY - minuteLength, centerX + minuteThickness / 2,
//                centerY + minuteThickness / 2), minuteThickness, minuteThickness, minutePaint);

        //画圆盘
//        minutePaint.setColor(0xff474747);
//        minutePaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(centerX, centerX, 20, minutePaint);
//        minutePaint.setColor(0xff474747);
//        minutePaint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(centerX, centerX, 20, minutePaint);

        //画秒针
        secondPaint.setColor(0xffe54747);
        secondPaint.setStyle(Paint.Style.FILL);
        canvas.rotate(degree, centerX, centerY);
        if (degree >= 0 && degree < 90) {

        }
        if (degree >= 90 && degree < 180) {
            secondPaint.setShadowLayer(6f,
                    6f * (float) Math.sin(180 - degree), 6f * (float) Math.cos(180 - degree),
                    Color.DKGRAY);
        }
// else if (degree >= 90 && degree < 180) {
//            secondPaint.setShadowLayer(12,
//                    6f * (float) Math.cos(degree - 90), (float) Math.sin(degree - 90), Color.DKGRAY);
//        } else if (degree >= 180 && degree < 270) {
//            secondPaint.setShadowLayer(12,
//                    -6f * (float) Math.sin(degree), -6f * (float) Math.cos(degree), Color.DKGRAY);
//        } else if (degree >= 270 && degree < 360) {
//            secondPaint.setShadowLayer(12,
//                    -6f * (float) Math.cos(degree - 90), -(float) Math.sin(degree - 90), Color.DKGRAY);
//        }
        canvas.drawRoundRect(new RectF(centerX - secondThickness / 2, centerY - secondLength, centerX + secondThickness / 2,
                centerY + secondThickness * 5), secondThickness, secondThickness, secondPaint);

        //画红色钉子
        canvas.restore();
        Bitmap redSnag = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.img_snag_red);
        canvas.drawBitmap(redSnag, null,
                new Rect(centerX - 12, centerY - 12, centerX + 12, centerY + 12), null);

        postInvalidate();
    }
}
