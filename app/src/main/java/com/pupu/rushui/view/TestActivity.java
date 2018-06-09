package com.pupu.rushui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.net.ApiClient;
import com.pupu.rushui.util.Logger;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/4/2.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_test;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        test();
    }

    @Override
    protected void initData() {

    }

    public void test() {
//        Call<ResponseBody> call = ApiClient.getInstance().getApi().testNet("xxx");
//        Logger.i("pupu", call.request().body().toString());
//        try {
//            Response<ResponseBody> response = call.execute();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Logger.i("pupu", response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        ApiClient.getInstance().getApi().testNet("@ctitle")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            System.out.println(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println(throwable.toString());
                    }
                });
    }
}
