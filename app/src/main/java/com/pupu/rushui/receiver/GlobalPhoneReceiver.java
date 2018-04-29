package com.pupu.rushui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.google.gson.Gson;
import com.pupu.rushui.R;
import com.pupu.rushui.alarmlist.RingActivity;
import com.pupu.rushui.common.Constant;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.util.Logger;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;

/**
 * Created by pupu on 2018/4/12.
 */

public class GlobalPhoneReceiver extends BroadcastReceiver {
    private static final String TAG = "GlobalPhoneReceiver";

    /**
     * 闹钟提醒通知栏id
     */
    final static int ID_NOTI_RING = 0x1001;

    MediaPlayer mediaPlayer;

    //单例类
    private static GlobalPhoneReceiver globalPhoneReceiver;

    public static synchronized GlobalPhoneReceiver getInstance() {
        synchronized (GlobalPhoneReceiver.class) {
            if (globalPhoneReceiver == null) {
                globalPhoneReceiver = new GlobalPhoneReceiver();
            }
            return globalPhoneReceiver;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.i(TAG, "手机状态:" + intent.getAction());
        globalPhoneReceiver = this;
        Context appContext = context.getApplicationContext();
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
        }
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
        }
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
        }
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
        }
        if (intent.getAction().equals(Constant.ALARM_WAKE_UP)) {
            //播放音乐
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(appContext, R.raw.ring);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            //震动
            CommonUtil.vibrateStart(appContext, new long[]{1000, 1000, 1000, 1000}, 0);
            //跳转页面
            String strTime = intent.getExtras().getString("alarmTime");
            Gson gson = new Gson();
            AlarmTime alarmTime = gson.fromJson(strTime, AlarmTime.class);
            Intent alarmIntent = new Intent(appContext, RingActivity.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            alarmIntent.putExtra("alarmTime", alarmTime);
            appContext.startActivity(alarmIntent);

            //创建通知栏
            JPushLocalNotification notification = new JPushLocalNotification();
            notification.setNotificationId(ID_NOTI_RING);
            notification.setContent(appContext.getString(R.string.str_wakeup));
            notification.setTitle(appContext.getString(R.string.str_alarmClock));
            notification.setExtras(strTime);
            JPushInterface.addLocalNotification(appContext, notification);
        }
        if (intent.getAction().equals(Constant.ALARM_CLOSE_RING)) {
            //关闭音乐
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            //取消震动
            CommonUtil.virateCancel(appContext);
            //杀掉对应的通知栏通知
            JPushInterface.removeLocalNotification(appContext, ID_NOTI_RING);
        }
    }
}
