package com.pupu.rushui.util;

import android.util.Log;

/**
 * Created by pulan on 17/12/5.
 */

public class Logger {
    //设为false关闭日志

    private static final boolean LOG_ENABLE = true;

    public static void i(String tag, String msg) {
        if (LOG_ENABLE) {
            //当文本量超过4K时，循环打印
            msg = msg.trim();
            int index = 0;
            int maxLength = 4000;
            String sub;
            while (index < msg.length()) {
                // java的字符不允许指定超过总的长度end
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, maxLength);
                }
                index += maxLength;
                Log.i(tag, sub.trim());
            }
        }
    }

    public static void v(String tag, String msg) {
        if (LOG_ENABLE) {
            //当文本量超过4K时，循环打印
            msg = msg.trim();
            int index = 0;
            int maxLength = 4000;
            String sub;
            while (index < msg.length()) {
                // java的字符不允许指定超过总的长度end
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, maxLength);
                }
                index += maxLength;
                Log.v(tag, sub.trim());
            }
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_ENABLE) {
            //当文本量超过4K时，循环打印
            msg = msg.trim();
            int index = 0;
            int maxLength = 4000;
            String sub;
            while (index < msg.length()) {
                // java的字符不允许指定超过总的长度end
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, maxLength);
                }
                index += maxLength;
                Log.d(tag, sub.trim());
            }
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_ENABLE) {
            //当文本量超过4K时，循环打印
            msg = msg.trim();
            int index = 0;
            int maxLength = 4000;
            String sub;
            while (index < msg.length()) {
                // java的字符不允许指定超过总的长度end
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, maxLength);
                }
                index += maxLength;
                Log.w(tag, sub.trim());
            }
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_ENABLE) {
            //当文本量超过4K时，循环打印
            msg = msg.trim();
            int index = 0;
            int maxLength = 4000;
            String sub;
            while (index < msg.length()) {
                // java的字符不允许指定超过总的长度end
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, maxLength);
                }
                index += maxLength;
                Log.e(tag, sub.trim());
            }
        }
    }
}
