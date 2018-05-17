package com.pupu.rushui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.pupu.rushui.util.Logger;

/**
 * Created by pupu on 2018/4/7.
 */

public class SlideAlphaView extends LinearLayout {

    private static final String TAG = "SlideAlphaView";
    float startX, startY;
    float curX, curY;
    float deta;
    public final static int MODE_SLIDE_UP = 0x001;//上滑渐变模式
    public final static int MODE_SLIDE_DOWN = 0x002;//下滑渐变模式
    public final static int MODE_BOTH = 0x003;//上滑or下滑均可渐变
    int mode = MODE_SLIDE_UP;

    public SlideAlphaView(Context context) {
        this(context, null);
    }

    public SlideAlphaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                if (mode == MODE_SLIDE_UP) {
                    if (curY < startY) {
                        deta = curY - startY;
                        setAlpha(1 - Math.abs(deta) / 600);
                    }
                } else if (mode == MODE_SLIDE_DOWN) {
                    if (curY > startY) {
                        deta = curY - startY;
                        setAlpha(1 - Math.abs(deta) / 600);
                    }
                } else {
                    deta = curY - startY;
                    setAlpha(1 - Math.abs(deta) / 600);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (onSlideLisenter != null) {
                    onSlideLisenter.onSlideOver((int) deta);
                }
                if (Math.abs(deta) < 600) {
                    setAlpha(1);
                } else {
                    setAlpha(0);
                    setVisibility(GONE);
                    setAlpha(1);
                }
                break;
        }
        return true;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    OnSlideLisenter onSlideLisenter;

    public interface OnSlideLisenter {
        void onSlideOver(int deta);
    }

    public void setOnSlideLisenter(OnSlideLisenter lisenter) {
        onSlideLisenter = lisenter;
    }

}
