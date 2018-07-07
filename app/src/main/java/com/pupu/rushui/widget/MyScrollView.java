package com.pupu.rushui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.pupu.rushui.util.Logger;

/**
 * Created by pupu on 2018/6/17.
 */

public class MyScrollView extends ScrollView {

    private float FistXLocation;
    private float FistYlocation;
    private boolean Istrigger = false;
    private final int TRIGER_LENTH = 50;
    private final int HORIZOTAL_LENTH = 20;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return false;
    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int deltaX = 0;
//        int deltaY = 0;
//
//        final float x = ev.getX();
//        final float y = ev.getY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                deltaX = (int)(FistXLocation - x);
//                deltaY = (int)(FistYlocation - y);
//                if (Math.abs(deltaY) > TRIGER_LENTH
//                        && Math.abs(deltaX) < HORIZOTAL_LENTH) {
//
//                    Istrigger = true;
//                    return super.onInterceptTouchEvent(ev);
//                    //拦截这个手势剩下的部分  ，使他不会响应viewpager的相关手势
//                }
//
//                return false;//没有触发拦截条件，不拦截事件，继续分发至viewpager
//
//            case MotionEvent.ACTION_DOWN:
//                FistXLocation = x;
//                FistYlocation = y;
//                if(getScaleY()<-400){
//                    System.out.println(getScaleY());
//                }
//                requestDisallowInterceptTouchEvent(false);
//                return  super.onInterceptTouchEvent(ev);
//
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                if (Istrigger) {
//
//                    Istrigger = false;
//                    return  super.onInterceptTouchEvent(ev);
//                }
//
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
}
