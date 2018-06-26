package com.pupu.rushui.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pupu.rushui.app.MyApplication;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.PhoneInfo;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.entity.WhiteNoise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulan on 2017/9/28.
 * 数据库辅助类
 */
public class DataPreference {
    static String TAG = "DataPreference";

    private DataPreference() {
        throw new AssertionError("Not allow");
    }

    /**
     * 保存手机相关的信息
     *
     * @param info
     */
    public static synchronized void setPhoneInfo(PhoneInfo info) {
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("phoneInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String tmp = gson.toJson(info);
        editor.putString("jsonInfo", tmp);
        editor.commit();
    }

    /**
     * 获取手机相关信息
     *
     * @return
     */
    public static PhoneInfo getPhoneInfo() {
        PhoneInfo info = null;
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("phoneInfo", Context.MODE_PRIVATE);
        String tmp = preferences.getString("jsonInfo", "");
        if (!tmp.equals("")) {
            Gson gson = new Gson();
            info = gson.fromJson(tmp, PhoneInfo.class);
            return info;
        }
        return info;
    }

    /**
     * 本地保存用户数据
     *
     * @param user
     */
    public static synchronized void setUserInfo(UserInfo user) {
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String tmp = gson.toJson(user);
        editor.putString("jsonInfo", tmp);
        editor.commit();
    }

    /**
     * 获取本地用户数据
     *
     * @return
     */
    public static UserInfo getUserInfo() {
        UserInfo user = null;
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String tmp = preferences.getString("jsonInfo", "");
        if (!tmp.equals("")) {
            Gson gson = new Gson();
            user = gson.fromJson(tmp, UserInfo.class);
            return user;
        }
        return user;
    }

    /**
     * 获取闹钟（唯一的）
     *
     * @return
     */
    public static AlarmTime getAlarm() {
        AlarmTime alarmTime = null;
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("alarm", Context.MODE_PRIVATE);
        String tmp = preferences.getString("jsonInfo", "");
        if (!tmp.equals("")) {
            Gson gson = new Gson();
            alarmTime = gson.fromJson(tmp, AlarmTime.class);
        }
        return alarmTime;
    }

    /**
     * 设置闹钟（唯一的）
     *
     * @param alarmTime
     */
    public static synchronized void setAlarm(AlarmTime alarmTime) {
        if (alarmTime == null) {
            return;
        }
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("alarm", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String tmp = gson.toJson(alarmTime);
        editor.putString("jsonInfo", tmp);
        editor.commit();
    }

    /**
     * 添加闹钟
     *
     * @param mAlarmTime
     */
    public static synchronized void addAlarm(AlarmTime mAlarmTime) {
        List<AlarmTime> list = getAlarmList();
        //判断id，如果id已存在则是更新
        if (list == null) {
            list = new ArrayList<>();
            list.add(mAlarmTime);
        } else {
            if (mAlarmTime.getId() > list.size()) {
                list.add(mAlarmTime);
            } else {
                list.set(mAlarmTime.getId() - 1, mAlarmTime);
            }
        }
        setAlarmList(list);
    }

    /**
     * 存储本地闹钟列表
     *
     * @param list
     */
    public static synchronized void setAlarmList(List<AlarmTime> list) {
        //转json
        Gson gson = new Gson();
        String tmp = gson.toJson(list);
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("alarmList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("jsonInfo", tmp);
        editor.commit();
    }

    /**
     * 获取本地闹钟列表
     *
     * @return
     */
    public static List<AlarmTime> getAlarmList() {
        List<AlarmTime> list = null;
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("alarmList", Context.MODE_PRIVATE);
        String tmp = preferences.getString("jsonInfo", "");
        if (!tmp.equals("")) {
            Gson gson = new Gson();
            list = gson.fromJson(tmp, new TypeToken<List<AlarmTime>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 获取本地白噪声数据
     *
     * @return
     */
    public static List<WhiteNoise> getWhiteNoiseList() {
        List<WhiteNoise> list = null;
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("whiteNoiseList", Context.MODE_PRIVATE);
        String tmp = preferences.getString("jsonInfo", "");
        if (!tmp.equals("")) {
            Gson gson = new Gson();
            list = gson.fromJson(tmp, new TypeToken<List<WhiteNoise>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 设置本地白噪声数据
     *
     * @param list 白噪声列表
     */
    public static synchronized void setWhiteNoiseList(List<WhiteNoise> list) {
        //转json
        Gson gson = new Gson();
        String tmp = gson.toJson(list);
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("whiteNoiseList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("jsonInfo", tmp);
        editor.commit();
    }

}
