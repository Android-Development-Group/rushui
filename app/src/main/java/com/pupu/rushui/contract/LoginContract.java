package com.pupu.rushui.contract;

import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.base.BaseView;

/**
 * Created by pupu on 2018/5/10.
 */

public class LoginContract {
    public interface View extends BaseView<Presenter> {
        /**
         * 请求失败
         */
        void onFailed(String msg);

        /**
         * 请求成功
         */
        void onSuccess();
    }

    public interface Presenter extends BasePresenter {
        /**
         * 账号密码登录
         *
         * @param phoneNum
         * @param passwd
         */
        void loginByPassword(String phoneNum, String passwd);

        /**
         * 手机验证码登录
         *
         * @param phoneNum
         */
        void loginByPhoneNum(String phoneNum);

        /**
         * 验证验证码
         * @param phoneNum
         * @param code
         */
        void verifyCode(String phoneNum,String code);
    }
}
