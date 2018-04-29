package com.pupu.rushui.common;

import android.os.Environment;

import com.pupu.rushui.app.MyApplication;

/**
 * Created by pulan on 18/3/13.
 */

public class Constant {
    public final static String CACHE_PATH = Environment.getExternalStorageDirectory() + "/" + MyApplication.getInstance().getPackageName() + "/cache/";
    public final static String BASE_URL = "http://news-at.zhihu.com/api/4/news/";

    /**
     * 闹钟响起事件
     */
    public final static String ALARM_WAKE_UP = "com.pupu.rushui.alarmwakeup";
    /**
     * 关闭闹钟
     */
    public final static String ALARM_CLOSE_RING = "com.pupu.rushui.alarmclosering";
}
