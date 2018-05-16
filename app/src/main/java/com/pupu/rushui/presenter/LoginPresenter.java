package com.pupu.rushui.presenter;

import android.text.TextUtils;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.contract.LoginContract;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.net.ApiClient;
import com.pupu.rushui.net.RequestCallback;
import com.pupu.rushui.net.ResponseError;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.util.DataPreference;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/5/10.
 */

public class LoginPresenter implements LoginContract.Presenter {

    @Override
    public void attachView(BaseView view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void loginByPhoneNum(String phoneNum, String passwd) {
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() < 11) {
            CommonUtil.showToast(R.string.toast_phonenum_err);
            return;
        }
        if (TextUtils.isEmpty(passwd) || passwd.length() < 6) {
            CommonUtil.showToast(R.string.toast_password_err);
            return;
        }
        ApiClient.getInstance().getApi().loginByPhoneNum(phoneNum, passwd)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new RequestCallback<UserInfo>() {
                    @Override
                    public void onResponse(UserInfo userInfo) {
                        //本地存用户
                        if (userInfo != null) {
                            DataPreference.setUserInfo(userInfo);
                        }
                    }

                });
    }
}
