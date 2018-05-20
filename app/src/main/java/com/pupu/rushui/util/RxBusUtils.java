package com.pupu.rushui.util;


import android.util.Log;

import com.hwangjr.rxbus.RxBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengyu on 2016/11/9.
 */
public class RxBusUtils {
    private static final String TAG = "RxBusUtils";
    /**
     * 消息过滤
     */
    public static Map<String, Long> messageFilter = new HashMap<>();
    ;


    private static final byte[] LOCK = new byte[8];

    /**
     * 发送 Rxbus消息
     *
     * @param tag    标记
     * @param params 参数
     */
    public static void post(String tag, Object params) {
//        if (doFiltration(tag)) {
        Log.v(TAG, "post  ------------> ture");
        RxBus.get().post(tag, params);
        // }
    }


//    /**
//     * 消息进行过滤
//     *
//     * @param TAG
//     * @return
//     */
//    private static boolean doFiltration(String TAG) {
//        synchronized (LOCK) {
//            if (messageFilter == null) {
//                messageFilter = new HashMap<String, Long>();
//                return true;
//            }
//            Long currentTime = System.currentTimeMillis();
//            Long postTime = messageFilter.get(TAG);
//            if (postTime == null) {
//                messageFilter.put(TAG, currentTime);
//                return true;
//            }
//            Long intervel = currentTime - postTime;
//            if (intervel > RxBusTagConstance.INTERVEL) {
//                messageFilter.put(TAG, currentTime);
//                D.v(TAG, "doFiltration  ------------> ture");
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }

    public static void unregister(Object object) {
        try {
            RxBus.get().unregister(object);
        } catch (Exception e) {
        }

    }

    public static void register(Object object) {
        try {
            RxBus.get().register(object);
        } catch (Exception e) {
        }

    }
}
