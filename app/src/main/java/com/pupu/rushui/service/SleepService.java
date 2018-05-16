package com.pupu.rushui.service;

import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.pupu.rushui.R;
import com.pupu.rushui.app.MyApplication;
import com.pupu.rushui.view.MainActivity;

/**
 * Created by pupu on 2018/4/7.
 */

public class SleepService extends Service {

    final static int NOTICE_ID = 0x1001;
    static SleepService instance;

    public static synchronized SleepService getInstance() {
        if (instance != null) {
            return instance;
        }
        return new SleepService();
    }

    //用于播放音乐等媒体资源
    private static MediaPlayer mediaPlayer;

    /**
     * 当前播放状态
     */
    public final static int STATE_NONE = 0;
    public final static int STATE_PLAYING = 1;
    public final static int STATE_PAUSEING = 2;
    static int playStatus = STATE_NONE;

    /**
     * 需要播放多长时间，默认循环播放
     */
    static int countDownTime = -1;
    /**
     * 用于倒计时的计时器
     */
    static CountDownTimer countDownTimer;
    /**
     * 播放多少分钟
     */
    static int minuteRepeat = 5;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建一个通知栏，做成前台service
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle(getString(R.string.app_name)) // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText(getString(R.string.str_slogen)) // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        Notification notification = builder.build(); // 获取构建好的Notification
        notification.sound = null;//设置为静音
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(NOTICE_ID, notification);// 开始前台服务

        mediaPlayer = MediaPlayer.create(MyApplication.getInstance(), R.raw.rain2);
        mediaPlayer.setLooping(true);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 开始睡眠业务
     */
    public static void startSleep() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0f, 0f);
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
            playStatus = STATE_PLAYING;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            countDownTimer = new CountDownTimer(minuteRepeat * 60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                    }
                }
            };
            countDownTimer.start();
        }
    }

    /**
     * 停止睡眠
     */
    public static void stopSleep() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(1f, 1f);
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
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        playStatus = STATE_NONE;
                    }
                }
            });
            fadeVol.start();
        }
    }

    /**
     * 开始播放助眠音乐
     */
    public static void startPlayWhiteNoise() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            playStatus = STATE_PLAYING;
        }
    }

    /**
     * 暂停播放助眠音乐
     */
    public static void pausePlayWhiteNoise() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playStatus = STATE_PAUSEING;
        }
    }

    /**
     * 获取当前播放状态
     *
     * @return
     */
    public static int getPlayStatus() {
        return playStatus;
    }

    /**
     * 播放闹钟
     */
    public static void playAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(MyApplication.getInstance(), R.raw.ring);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
