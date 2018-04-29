package com.pupu.rushui.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pupu.rushui.app.MyApplication;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.PhoneInfo;
import com.pupu.rushui.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulan on 2017/9/28.
 * 数据库辅助类
 */
public class DataPreference {
    static String TAG = "DataPreference";

    /**
     * 保存手机相关的信息
     *
     * @param info
     */
    public static void setPhoneInfo(PhoneInfo info) {
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
        PhoneInfo info = new PhoneInfo();
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
    public static void setUserInfo(User user) {
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
    public static User getUserInfo() {
        User user = new User();
        SharedPreferences preferences = MyApplication.getInstance()
                .getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String tmp = preferences.getString("jsonInfo", "");
        if (!tmp.equals("")) {
            Gson gson = new Gson();
            user = gson.fromJson(tmp, User.class);
            return user;
        }
        return user;
    }

    /**
     * 添加闹钟
     *
     * @param mAlarmTime
     */
    public static void addAlarm(AlarmTime mAlarmTime) {
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
    public static void setAlarmList(List<AlarmTime> list) {
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

}
