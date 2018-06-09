package com.pupu.rushui.net;

import com.pupu.rushui.common.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pupu on 2018/5/10.
 * 网络请求客户端
 */

public class ApiClient {
    private static ApiClient instance;
    /**
     * 默认请求类型：异步请求
     */
    Api api;
    /**
     * 同步请求
     */
    Api syncApi;

    public static synchronized ApiClient getInstance() {
        synchronized (ApiClient.class) {
            if (instance == null) {
                instance = new ApiClient();
            }
            return instance;
        }
    }

    private ApiClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置超时时间
        builder.connectTimeout(Constant.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        builder.readTimeout(Constant.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        Retrofit asyncRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        api = asyncRetrofit.create(Api.class);

        Retrofit syncRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        syncApi = syncRetrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

    public Api getSyncApi() {
        return syncApi;
    }
}
