package com.pupu.rushui.presenter;

import android.os.Handler;
import android.os.Message;

import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.contract.MainContract;
import com.pupu.rushui.service.SleepService;

/**
 * Created by pupu on 2018/4/7.
 */

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;

    @Override
    public void attachView(BaseView view) {
        this.view = (MainContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void startSleep() {
        view.startSleep();
        //延时1s开始播放助眠音乐
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SleepService.startSleep();
            }
        }.sendEmptyMessageDelayed(0x01, 1000);
    }

    @Override
    public void stopSleep() {
        view.stopSleep();
        SleepService.stopSleep();
        view.initSleep();
    }

    @Override
    public void initSleep() {
        view.initSleep();
    }

    @Override
    public void controlPlay() {
        if (SleepService.getPlayStatus() == SleepService.STATE_PAUSEING) {
            view.resumePlay();
            SleepService.startPlayWhiteNoise();
        } else {
            view.pausePlay();
            SleepService.pausePlayWhiteNoise();
        }
    }
}
