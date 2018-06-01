package com.pupu.rushui.presenter;

import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.contract.LoginContract;
import com.pupu.rushui.datasource.RushuiDataSource;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.net.ApiClient;
import com.pupu.rushui.util.DataPreference;

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
        ApiClient.getInstance().getApi().loginByPhoneNum(phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        if (view != null) {
                            //本地存储
                            dataSource.setUserInfo(userInfo);
                            view.onSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (view != null) {
                            //提示出错
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
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        if (view != null) {
                            dataSource.setUserInfo(userInfo);
                            view.onSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (view != null) {
                            view.onFailed(throwable.getMessage());
                        }
                    }
                });
    }
}
