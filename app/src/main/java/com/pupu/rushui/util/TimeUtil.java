package com.pupu.rushui.util;

import com.pupu.rushui.entity.AlarmTime;

/**
 * Created by pupu on 2018/6/29.
 * 时间管理计算等工具类
 */

public class TimeUtil {

    private TimeUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * 计算两个时间的时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return int[0]==>小时差；int[1]==>分钟差;int[3]==>秒差
     */
    public static int[] caculateDuration(AlarmTime startTime, AlarmTime endTime) {
        int[] deta = new int[3];
        int start = parseTime2Second(startTime);
        int end = parseTime2Second(endTime);
        if (start <= end) {
            deta[0] = parseSecond2Time(end - start)[0];
            deta[1] = parseSecond2Time(end - start)[1];
            deta[2] = parseSecond2Time(end - start)[2];
        } else {
            deta[0] = parseSecond2Time(24 * 60 * 60 - end + start)[0];
            deta[1] = parseSecond2Time(24 * 60 * 60 - end + start)[1];
            deta[2] = parseSecond2Time(24 * 60 * 60 - end + start)[2];
        }
        return deta;
    }

    /**
     * 时间转换成秒
     *
     * @param alarmTime 指定时间
     * @return
     */
    public static int parseTime2Second(AlarmTime alarmTime) {
        return alarmTime.getHour24() * 60 * 60 + alarmTime.getMinute() * 60 + alarmTime.getSecond() * 60;
    }

    /**
     * 时间秒转换成时间
     *
     * @param second 指定秒
     * @return int[0]==>小时；int[1]==>分钟；int[2]==>秒
     */
    public static int[] parseSecond2Time(int second) {
        int hour = second / (60 * 60);
        int minute = second / 60;
        int sec = second % 60;
        int[] time = new int[3];
        time[0] = hour;
        time[1] = minute;
        time[2] = sec;
        return time;
    }
}
