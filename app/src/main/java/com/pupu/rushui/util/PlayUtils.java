package com.pupu.rushui.util;

import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.net.Uri;

import com.pupu.rushui.app.MyApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/6/26.
 * 声音播放工具类
 */

public class PlayUtils {

    static PlayUtils instance;

    MediaPlayer mediaPlayer;

    private PlayUtils() {
    }

    public synchronized static PlayUtils getInstance() {
        synchronized (PlayUtils.class) {
            if (instance == null) {
                instance = new PlayUtils();
            }
            return instance;
        }
    }

    /**
     * 音频id
     *
     * @param rawResId
     */
    public PlayUtils loadVoiceUrl(int rawResId) {
        mediaPlayer = MediaPlayer.create(MyApplication.getInstance(), rawResId);
        return this;
    }

    /**
     * 加载音频文件
     *
     * @param voiceUrl 音频路径
     */
    public PlayUtils loadVoiceUrl(String voiceUrl) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(MyApplication.getInstance(), Uri.parse(voiceUrl));
            mediaPlayer.prepare();
            //设置准备监听

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 开始播放
     */
    public PlayUtils start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        return this;
    }

    /**
     * 延时播放
     *
     * @param sec 指定延时时间（单位：s）
     */
    public PlayUtils startDelay(int sec) {
        if (sec == 0) {
            sec = 2;
        }
        Observable.timer(sec, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                        }
                    }
                });
        return this;
    }

    /**
     * 播放，带渐入
     */
    public PlayUtils startWithFade() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0f, 0f);
            mediaPlayer.start();
            ValueAnimator fadeVol = ValueAnimator.ofFloat(0f, 1f);
            fadeVol.setDuration(2000);
            fadeVol.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (mediaPlayer != null) {
                        mediaPlayer.setVolume((float) animation.getAnimatedValue(),
                                (float) animation.getAnimatedValue());
                    }
                }
            });
            fadeVol.start();
        }
        return this;
    }

    /**
     * 结束播放，带渐出
     */
    public PlayUtils stopWithFade() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(1f, 1f);
            ValueAnimator fadeVol = ValueAnimator.ofFloat(1f, 0f);
            fadeVol.setDuration(2000);
            fadeVol.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (mediaPlayer != null) {
                        mediaPlayer.setVolume((float) animation.getAnimatedValue(),
                                (float) animation.getAnimatedValue());
                    }
                    if ((float) animation.getAnimatedValue() <= 0) {
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                    }
                }
            });
            fadeVol.start();
        }
        return this;
    }

    /**
     * 停止播放
     */
    public PlayUtils stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        return this;
    }

    /**
     * 暂停播放
     */
    public PlayUtils pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        return this;
    }

    /**
     * 带渐出的暂停
     */
    public PlayUtils pauseWithFade() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(1f, 1f);
            ValueAnimator fadeVol = ValueAnimator.ofFloat(1f, 0f);
            fadeVol.setDuration(2000);
            fadeVol.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (mediaPlayer != null) {
                        mediaPlayer.setVolume((float) animation.getAnimatedValue(),
                                (float) animation.getAnimatedValue());
                    }
                    if ((float) animation.getAnimatedValue() <= 0) {
                        if (mediaPlayer != null) {
                            mediaPlayer.pause();
                        }
                    }
                }
            });
            fadeVol.start();
        }
        return this;
    }
}
