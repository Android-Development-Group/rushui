package com.pupu.rushui.net;

import com.pupu.rushui.common.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pupu on 2018/5/10.
 * 网络请求客户端
 */

public class ApiClient {
    private static ApiClient instance;
    Api api;

    public static synchronized ApiClient getInstance() {
        synchronized (ApiClient.class) {
            if (instance == null) {
                instance = new ApiClient();
            }
            return instance;
        }
    }

    private ApiClient() {
        Retrofit ribaoRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        api = ribaoRetrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
