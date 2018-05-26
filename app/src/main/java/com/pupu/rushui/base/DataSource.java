package com.pupu.rushui.base;

import com.pupu.rushui.entity.AlarmTime;

import rx.Observable;

/**
 * Created by pupu on 2018/5/26.
 */

public interface DataSource {

    /**
     * 设置闹钟
     * @param alarmTime
     */
    void setAlarm(AlarmTime alarmTime);

    /**
     * 获取闹钟
     * @return
     */
    Observable<AlarmTime> getAlarm();
}
