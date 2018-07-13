package com.pupu.rushui.presenter;

import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.contract.LoginContract;
import com.pupu.rushui.datasource.RushuiDataSource;
import com.pupu.rushui.net.ApiClient;
import com.pupu.rushui.net.BaseResponseFunc;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/5/10.
 */

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View view;
    RushuiDataSource dataSource;

    public LoginPresenter() {
        if (dataSource == null) {
            dataSource = new RushuiDataSource();
        }
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (LoginContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loginByPassword(String phoneNum, String passwd) {

    }

    @Override
    public void loginByPhoneNum(String phoneNum) {
        ApiClient.getInstance().getApi().requestSMSCode(phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new BaseResponseFunc<String>())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (view != null) {
                            view.onSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (view != null) {
                            view.onFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void verifyCode(String phoneNum, String code) {
        ApiClient.getInstance().getApi().verifySMSCode(phoneNum, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new BaseResponseFunc<String>())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (view != null) {
                            view.onSuccess();
                        }
                        //上传本地用户信息

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (view != null) {
                            view.onFailed(throwable.getMessage());
                        }
                    }
                });
    }
}
