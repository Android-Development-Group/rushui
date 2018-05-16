package com.pupu.rushui.contract;

import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.base.BaseView;

/**
 * Created by pupu on 2018/5/10.
 */

public class LoginContract {
    public interface View extends BaseView<Presenter> {
        /**
         * 去首页
         */
        void goHome();
    }

    public interface Presenter extends BasePresenter {
        /**
         * 登录
         */
        void loginByPhoneNum(String phoneNum,String passwd);
    }
}
