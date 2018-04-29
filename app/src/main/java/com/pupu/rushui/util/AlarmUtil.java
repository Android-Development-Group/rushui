package com.pupu.rushui.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.pupu.rushui.app.MyApplication;
import com.pupu.rushui.common.Constant;
import com.pupu.rushui.entity.AlarmTime;

import java.util.Calendar;

/**
 * Created by pupu on 2018/4/15.
 */

public class AlarmUtil {

    private static final String TAG = AlarmUtil.class.getName();

    /**
     * 向系统注册时间闹钟
     *
     * @param mAlarmTime
     */
    public static void registerAlarm(AlarmTime mAlarmTime) {
        AlarmManager alarmManager = (AlarmManager) MyApplication.getInstance()
                .getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mAlarmTime.getHour24());
        calendar.set(Calendar.HOUR, mAlarmTime.getHour());
        calendar.set(Calendar.MINUTE, mAlarmTime.getMinute());
        Intent intent = new Intent();
        intent.setAction(Constant.ALARM_WAKE_UP);
        Gson gson = new Gson();
        String time = gson.toJson(mAlarmTime);
        intent.putExtra("time", time);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(MyApplication.getInstance(), 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
