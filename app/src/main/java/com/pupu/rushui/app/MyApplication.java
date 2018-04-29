package com.pupu.rushui.app;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

import com.pupu.rushui.common.Constant;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.receiver.GlobalPhoneReceiver;
import com.pupu.rushui.util.AlarmManagerUtil;
import com.pupu.rushui.util.DataPreference;

import java.util.List;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by pupu on 2017/9/22.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //init Bugly
//        Bugly.init(getApplicationContext(), "8d9051b815", false);

        //异常捕获初始化
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //极光统计
        JAnalyticsInterface.setDebugMode(true);
        JAnalyticsInterface.init(this);

        // 初始化BmobSDK
//        Bmob.initialize(this, "8dd26f729626ec7ce2961cf66bc53e65");

        //手机全局广播监听,这里添加屏幕亮灭的监听
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //每分钟都检测一次服务和MQTT状态
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction(Constant.ALARM_WAKE_UP);
        filter.addAction(Constant.ALARM_CLOSE_RING);
        this.registerReceiver(new GlobalPhoneReceiver(), filter);

        //重新注册所有的闹钟
        new Thread(new Runnable() {
            @Override
            public void run() {
                registerAllAlarm();
            }
        }).start();
    }

    /**
     * 注册所有的闹钟
     */
    private void registerAllAlarm() {
        List<AlarmTime> list = DataPreference.getAlarmList();
        if (list != null) {
            for (AlarmTime alarmTime : list) {
                AlarmManagerUtil.setAlarm(mContext, 1, alarmTime,
                        alarmTime.getHour24(), alarmTime.getMinute(), alarmTime.getId(),
                        0, "", 2);
            }
        }
    }

    public synchronized static MyApplication getInstance() {
        synchronized (MyApplication.class) {
            if (mContext == null) {
                mContext = new MyApplication();
            }
            return mContext;
        }
    }
}
