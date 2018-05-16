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
import com.pupu.rushui.widget.SlideAlphaView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pupu on 2018/4/17.
 */

public class RingActivity extends Activity {

    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.sav)
    SlideAlphaView sav;

    /**
     * 闹钟时间
     */
    AlarmTime mAlarmTime;

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

        //开启上下抖动动画
        final Window window = getWindow();
        final WindowManager.LayoutParams wl = window.getAttributes();

        ValueAnimator animator = ValueAnimator.ofInt(-100, 100);
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

        sav.setOnSlideLisenter(new SlideAlphaView.OnSlideLisenter() {
            @Override
            public void onSlideOver(int deta) {
                if (deta > 500) {
                    //通知关闭页面
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
