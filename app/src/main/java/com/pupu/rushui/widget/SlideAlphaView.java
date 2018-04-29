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
                Logger.i(TAG, "deta==>" + deta);
                curX = event.getX();
                curY = event.getY();
                if (curY < startY) {
                    deta = Math.abs(curY - startY);
                    setAlpha(1 - deta / 600);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (onSlideLisenter != null) {
                    onSlideLisenter.onSlideOver((int) deta);
                }
                if (deta < 600) {
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

    OnSlideLisenter onSlideLisenter;

    public interface OnSlideLisenter {
        void onSlideOver(int deta);
    }

    public void setOnSlideLisenter(OnSlideLisenter lisenter) {
        onSlideLisenter = lisenter;
    }

}
