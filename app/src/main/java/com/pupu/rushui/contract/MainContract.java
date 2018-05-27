package com.pupu.rushui.contract;

import android.app.Activity;

import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.entity.AlarmTime;

/**
 * Created by pupu on 2018/4/7.
 */

public class MainContract {
    public interface View extends BaseView {

        /**
         * 准备睡眠
         */
        void preSleep();

        /**
         * 开始睡眠
         */
        void startSleep(AlarmTime alarmTime);

        /**
         * 停止睡眠
         */
        void stopSleep();

        /**
         * 开始播放助眠音乐
         */
        void startPlay();

        /**
         * 停止播放
         */
        void stopPlay();

        /**
         * 引导注册或登录
         */
        void remindLoginOrRegister();
    }

    public interface Presenter extends BasePresenter {

        /**
         * 准备睡眠
         */
        void preSleep();

        /**
         * 开始睡眠
         */
        void startSleep();

        /**
         * 停止睡眠
         */
        void stopSleep();
    }
}
