package com.pupu.rushui.contract;

import android.app.Activity;

import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.base.BaseView;

/**
 * Created by pupu on 2018/4/7.
 */

public class MainContract {
    public interface View extends BaseView {
        /**
         * 开启按钮的呼吸动画
         */
        void startBtnSleepAnim();

        /**
         * 睡眠初始化状态
         */
        void initSleep();

        /**
         * 开始睡眠
         */
        void startSleep();

        /**
         * 停止睡眠
         */
        void stopSleep();

        /**
         * 暂停播放
         */
        void pausePlay();

        /**
         * 继续播放
         */
        void resumePlay();

    }

    public interface Presenter extends BasePresenter {
        /**
         * 开始睡眠
         */
        void startSleep();

        /**
         * 停止睡眠
         */
        void stopSleep();

        /**
         * 初始化睡眠
         */
        void initSleep();

        /**
         * 暂停播放
         */
        void controlPlay();
    }
}
