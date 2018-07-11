package com.pupu.rushui.net;

import com.pupu.rushui.util.DataPreference;
import com.pupu.rushui.util.TimeUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by pupu on 2018/7/10.
 * 入睡请求&响应拦截器
 */

public class RushuiInterceptor implements Interceptor {

    /**
     * 授权请求头token
     */
    private static final String AUTHORIZATION = "token";

    /**
     * 时间戳
     */
    private static final String TIMESTAMP = "timestamp";

    /**
     * 请求字段md5加密校验
     */
    private static final String SIGN = "sign";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //添加请求头
        Request.Builder requestBuilder = original.newBuilder();
//        if (DataPreference.getUserInfo() != null && DataPreference.getUserInfo().getUserToken() != null) {
//            requestBuilder.addHeader(AUTHORIZATION, DataPreference.getUserInfo().getUserToken());
//        }
        requestBuilder.addHeader(TIMESTAMP, TimeUtil.formatDate(new Date()));
        requestBuilder.addHeader(SIGN, "");
        Request request = requestBuilder.build();
        Response response = chain.proceed(request);
        return response;
    }
}
