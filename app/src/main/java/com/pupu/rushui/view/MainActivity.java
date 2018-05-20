package com.pupu.rushui.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.common.RxBusConstant;
import com.pupu.rushui.contract.MainContract;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.presenter.MainPresenter;
import com.pupu.rushui.service.SleepService;
import com.pupu.rushui.util.DataPreference;
import com.pupu.rushui.util.Logger;
import com.pupu.rushui.util.RxBusUtils;
import com.pupu.rushui.widget.RoundProgressBar;
import com.pupu.rushui.widget.SlideAlphaView;
import com.pupu.rushui.widget.TimeDiskView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String TAG = MainActivity.class.getName();
    @Bind(R.id.btn_startSleep)
    Button btn_startSleep;
    Animation breathAnim;
    @Bind(R.id.layout_bottomBtn)
    View layout_bottomBtn;
    @Bind(R.id.layout_sleeping)
    SlideAlphaView layout_sleeping;
    @Bind(R.id.tdv)
    TimeDiskView tdv;
    @Bind(R.id.iv_playController)
    ImageView iv_playController;
    @Bind(R.id.rpb_play)
    RoundProgressBar rpb_play;
    @Bind(R.id.tv_remind)
    TextView tv_remind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getPresenter().initSleep();
        RxBusUtils.register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBusUtils.unregister(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new MainPresenter();
        }
        return (MainContract.Presenter) this.presenter;
    }

    @Override
    protected void initView() {
        rpb_play.setMax(SleepService.getPlayDuration());
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.btn_startSleep, R.id.iv_mine, R.id.iv_setting,
            R.id.iv_playController})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_playController:
                getPresenter().controlPlay();
                break;
            case R.id.btn_startSleep:
                getPresenter().startSleep();
                break;
            case R.id.iv_mine:
                break;
            case R.id.iv_setting:
                start2Activity(SettingActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void startBtnSleepAnim() {
        if (breathAnim != null) {
            breathAnim.cancel();
            breathAnim = null;
        }
        breathAnim = new ScaleAnimation(1.1f, 1f, 1.1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        breathAnim.setRepeatMode(Animation.REVERSE);
        breathAnim.setRepeatCount(Animation.INFINITE);
        breathAnim.setDuration(2500);
        breathAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        btn_startSleep.startAnimation(breathAnim);
    }

    @Override
    public void initSleep() {
        tv_title.setText(R.string.app_name);

        startBtnSleepAnim();

        layout_sleeping.setOnSlideLisenter(new SlideAlphaView.OnSlideLisenter() {
            @Override
            public void onSlideOver(int deta) {
                if (Math.abs(deta) >= 500) {
                    //停止睡眠
                    getPresenter().stopSleep();
                }
            }
        });
        tdv.start();

        tdv.showTimeText();
    }

    @Override
    public void startSleep() {
        //隐藏时间文字
        tdv.hideTimeText();

        rpb_play.setMax(SleepService.getPlayDuration());
        rpb_play.setProgress(SleepService.getPlayDuration());

        //设置播放图片
        iv_playController.setImageResource(R.mipmap.img_pause);

        //隐藏底部三个按钮
        btn_startSleep.clearAnimation();
        Animation alphaAnim = new AlphaAnimation(1f, 0f);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout_bottomBtn.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnim.setDuration(1500);
        layout_bottomBtn.startAnimation(alphaAnim);

        //显示播放按钮
        Animation alphaAnim2 = new AlphaAnimation(0f, 1f);
        alphaAnim2.setDuration(2000);
        alphaAnim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layout_sleeping.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        layout_sleeping.startAnimation(alphaAnim2);

        startService(new Intent(this, SleepService.class));

        //设置预计睡眠时间
        AlarmTime alarmTime = DataPreference.getAlarm();
        if (alarmTime != null) {
            AlarmTime curTime = new AlarmTime();
            curTime.setHour24(
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            );
            int deta = 0;
            if (curTime.getHour24() > alarmTime.getHour24()) {
                deta = 23 - curTime.getHour24() + alarmTime.getHour24() + 1;
            } else {
                deta = alarmTime.getHour24() - curTime.getHour24();
            }
            tv_remind.setText(String.format(
                    getString(R.string.str_sleepRemind), deta
            ));
        } else {
            tv_remind.setText(R.string.str_notSetAlarm);
        }

    }

    @Override
    public void stopSleep() {
        layout_bottomBtn.setVisibility(View.VISIBLE);
        start2Activity(SleepResultActivity.class);
    }

    @Override
    public void pausePlay() {
        iv_playController.setImageResource(R.mipmap.img_play);
    }

    @Override
    public void resumePlay() {
        iv_playController.setImageResource(R.mipmap.img_pause);
    }


    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(RxBusConstant.EVENT_PLAYING_WHITENOISE)
            }
    )
    public void updatePlayState(String progress) {
        Logger.i(TAG, "progress==>" + progress);
        int p = Integer.parseInt(progress);
        rpb_play.setProgress(p);
    }

}
