package com.pupu.rushui.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by pupu on 2018/4/15.
 */

public class AlarmTime implements Serializable {

    int id;
    int hour24, hour, minute, second;
    boolean isNoon;
    int year, month, day;
    /**
     * 一周之内的哪些天有效
     */
    boolean[] weeks = new boolean[7];

    public AlarmTime() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour24() {
        return hour24;
    }

    public void setHour24(int hour24) {
        this.hour24 = hour24;
        if (hour24 > 12) {
            isNoon = true;
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public boolean isNoon() {
        return isNoon;
    }

    public void setNoon(boolean noon) {
        isNoon = noon;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "AlarmTime{" +
                "id=" + id +
                ", hour24=" + hour24 +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", isNoon=" + isNoon +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", weeks=" + Arrays.toString(weeks) +
                '}';
    }
}
