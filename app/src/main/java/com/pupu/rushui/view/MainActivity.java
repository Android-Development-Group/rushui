package com.pupu.rushui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.pupu.rushui.widget.HintPopupWindow;
import com.pupu.rushui.widget.RoundProgressBar;
import com.pupu.rushui.widget.SlideAlphaView;
import com.pupu.rushui.widget.TimeDiskView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String TAG = MainActivity.class.getName();
    @BindView(R.id.btn_startSleep)
    Button btn_startSleep;
    Animation breathAnim;
    @BindView(R.id.layout_bottomBtn)
    View layout_bottomBtn;
    @BindView(R.id.layout_sleeping)
    SlideAlphaView layout_sleeping;
    @BindView(R.id.tdv)
    TimeDiskView tdv;
    @BindView(R.id.tv_remind)
    TextView tv_remind;

    /**
     * 播放助眠音乐的player
     */
    MediaPlayer mediaPlayer;
    /**
     * 播放时长，默认3分钟
     */
    final static int TIME_PLAY_DURATION = 3 * 60 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        RxBusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBusUtils.unregister(this);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
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
        getPresenter().preSleep();
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.btn_startSleep, R.id.iv_mine, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
    public void preSleep() {
        tv_title.setText(R.string.app_name);

        if (layout_bottomBtn.getVisibility() == View.GONE) {
            layout_bottomBtn.setVisibility(View.VISIBLE);
        }
        if (layout_sleeping.getVisibility() == View.VISIBLE) {
            layout_sleeping.setVisibility(View.GONE);
        }

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

        //初始化助眠音乐
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.rain2);
        }
    }

    @Override
    public void startSleep(AlarmTime alarmTime) {
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
        alphaAnim2.setDuration(3000);
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
        //开启睡眠守护service
        startService(new Intent(this, SleepService.class));

        //延后2s开始播放助眠音乐
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startPlay();
                    }
                });
    }

    @Override
    public void stopSleep() {
        preSleep();
        start2Activity(SleepResultActivity.class);
        //停止播放
        stopPlay();
    }

    @Override
    public void startPlay() {
        Logger.i(TAG, "startPlay");
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.rain2);
        }
        mediaPlayer.setVolume(0f, 0f);
        //渐变播放
        ValueAnimator fadeVol = ValueAnimator.ofFloat(0f, 1f);
        fadeVol.setDuration(2000);
        fadeVol.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mediaPlayer.setVolume((float) animation.getAnimatedValue(),
                        (float) animation.getAnimatedValue());
            }
        });
        fadeVol.start();
        mediaPlayer.start();
        //开启倒计时停止播放
        Observable.timer(TIME_PLAY_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        stopPlay();
                    }
                });
    }

    @Override
    public void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(1f, 1f);
            //渐变关闭
            ValueAnimator fadeVol = ValueAnimator.ofFloat(1f, 0f);
            fadeVol.setDuration(2000);
            fadeVol.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mediaPlayer.setVolume((float) animation.getAnimatedValue(),
                            (float) animation.getAnimatedValue());
                    if ((float) animation.getAnimatedValue() <= 0) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            });
            fadeVol.start();
        }
    }

    @Override
    public void remindLoginOrRegister() {
        View iv_mine = findViewById(R.id.iv_mine);
        if (iv_mine != null) {
            //弹出提示框
            List<String> contentList = new ArrayList<>();
            contentList.add(getString(R.string.str_remindRegister));
            List<View.OnClickListener> clickListenerList = new ArrayList<>();
            clickListenerList.add(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转注册页

                }
            });
            HintPopupWindow tmpWindow = new HintPopupWindow(this, contentList, clickListenerList);
            tmpWindow.showPopupWindow(iv_mine);
        }
    }
}
