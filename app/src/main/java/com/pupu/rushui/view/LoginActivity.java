package com.pupu.rushui.view;

import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.contract.LoginContract;
import com.pupu.rushui.presenter.LoginPresenter;

/**
 * Created by pupu on 2018/5/10.
 */

public class LoginActivity extends BaseActivity<LoginContract.Presenter> {
    @Override
    protected int setLayoutResourceID() {
        return 0;
    }

    @Override
    protected LoginContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new LoginPresenter();
        }
        return presenter;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
