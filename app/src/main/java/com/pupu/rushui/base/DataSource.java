package com.pupu.rushui.base;

import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.PhoneInfo;

import rx.Observable;

/**
 * Created by pupu on 2018/5/26.
 */

public interface DataSource {

    /**
     * 设置闹钟
     *
     * @param alarmTime
     */
    void setAlarm(AlarmTime alarmTime);

    /**
     * 获取闹钟
     *
     * @return
     */
    Observable<AlarmTime> getAlarm();

    /**
     * 获取手机自定义配置信息等
     *
     * @return
     */
    Observable<PhoneInfo> getPhoneInfo();

    /**
     * 设置手机自定义配置
     * @param phoneInfo
     */
    void setPhoneInfo(PhoneInfo phoneInfo);
}
