package com.pupu.rushui.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.common.Constant;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.widget.SlideAlphaView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pupu on 2018/4/17.
 */

public class RingActivity extends Activity {

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.sav)
    SlideAlphaView sav;

    /**
     * 闹钟时间
     */
    AlarmTime mAlarmTime;

    Window window;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        ButterKnife.bind(this);
        setFinishOnTouchOutside(false);
        initView();
    }

    private void initView() {
        if (getIntent().getExtras() != null) {
            mAlarmTime = (AlarmTime) getIntent().getExtras().getSerializable("alarmTime");
            if (mAlarmTime != null) {
                String hour, minute;
                if (mAlarmTime.getHour24() < 10) {
                    hour = "0" + String.valueOf(mAlarmTime.getHour24());
                } else {
                    hour = String.valueOf(mAlarmTime.getHour24());
                }

                if (mAlarmTime.getMinute() < 10) {
                    minute = "0" + String.valueOf(mAlarmTime.getMinute());
                } else {
                    minute = String.valueOf(mAlarmTime.getMinute());
                }
                tv_time.setText(hour + " : " + minute);
            }
        }

        window = getWindow();
        final WindowManager.LayoutParams wl = window.getAttributes();

        //设置对话框大小
        wl.width = CommonUtil.getDisplayPxWidth(this) - CommonUtil.dpToPx(this, 16);
        wl.height = CommonUtil.getDisplayPxHeight(this) - CommonUtil.dpToPx(this, 128);
        wl.y = 0;
        window.setAttributes(wl);

        //开启上下抖动动画
        ValueAnimator animator = ValueAnimator.ofInt(-20, 20);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                wl.y = (int) animation.getAnimatedValue();
                window.setAttributes(wl);
            }
        });
        animator.start();
        sav.setMode(SlideAlphaView.MODE_BOTH);
        sav.setOnSlideLisenter(new SlideAlphaView.OnSlideLisenter() {
            @Override
            public void onSlideOver(int deta) {
                //判断上滑下滑
                if (Math.abs(deta) >= 500) {
                    if (deta > 0) {
                        //下滑

                    } else {
                        //上滑
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public void finish() {
        //发送关闭闹钟广播
        Intent closeRingIt = new Intent();
        closeRingIt.setAction(Constant.ALARM_CLOSE_RING);
        sendBroadcast(closeRingIt);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
