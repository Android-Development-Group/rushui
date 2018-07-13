package com.pupu.rushui.net;

/**
 * Created by pupu on 2018/6/1.
 */

public interface NetAction {

    String REQUEST_SMS_CODE = "/smsCode";

    String VERIFY_SMS_CODE = "/smsCodeVerify";

    String REQUEST_AD = "/ad";

    String REQUEST_USER = "/user";

    String REQUEST_WHITE_NOISE_LIST = "/whiteNoise";

    String REQUEST_FEED_BACK = "/feedBack";

    String REQUEST_SLEEP_DATA_LIST = "/sleepData";

    String UPLOAD_SLEEP_DATA_LIST = "/uploadSleepData";

    String UPLOAD_USER_INFO = "/userRequest";
}
