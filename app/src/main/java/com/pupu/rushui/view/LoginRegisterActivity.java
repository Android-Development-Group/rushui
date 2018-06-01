package com.pupu.rushui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.contract.LoginContract;
import com.pupu.rushui.presenter.LoginPresenter;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.widget.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/5/10.
 */

public class LoginRegisterActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_prePhoneNum)
    TextView tv_prePhoneNum;
    @BindView(R.id.et_phoneNum)
    EditText et_phoneNum;
    @BindView(R.id.btn_login)
    LoadingButton btn_login;

    String phoneNum;

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
        return R.layout.activity_loginregister;
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
        tv_title.setText(R.string.str_welcome);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onFailed(String msg) {
        CommonUtil.showToast(msg);
        btn_login.setEnabled(true);
        btn_login.stopLoading();
    }

    @Override
    public void onSuccess() {
        btn_login.stopLoading();
        //跳转验证码页面
        Bundle bundle = new Bundle();
        bundle.putString("phoneNum", phoneNum);
        start2Activity(VerifyCodeActivity.class, bundle);
    }

    @OnClick({
            R.id.btn_login, R.id.tv_prePhoneNum, R.id.tv_passwordLogin
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                phoneNum = et_phoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum)) {
                    CommonUtil.showToast(R.string.toast_phonenum_null);
                    return;
                }
                if (!CommonUtil.isPhoneNum(phoneNum)) {
                    CommonUtil.showToast(R.string.toast_phonenum_err);
                    return;
                }
                btn_login.setEnabled(false);
                btn_login.startLoading();
                getPresenter().loginByPhoneNum(phoneNum);
                break;
            case R.id.tv_prePhoneNum:

                break;
            case R.id.tv_passwordLogin:
                break;
        }
    }
}
