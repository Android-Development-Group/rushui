package com.pupu.rushui.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseFragment;
import com.pupu.rushui.contract.LoginContract;
import com.pupu.rushui.presenter.LoginPresenter;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.view.LoginRegisterActivity;
import com.pupu.rushui.widget.LoadingButton;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/6/2.
 */

public class PhoneNumFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_prePhoneNum)
    TextView tv_prePhoneNum;
    @BindView(R.id.et_phoneNum)
    EditText et_phoneNum;
    @BindView(R.id.btn_login)
    LoadingButton btn_login;

    String phoneNum;

    @Override
    protected LoginContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new LoginPresenter();
        }
        return presenter;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_phonenum;
    }

    @Override
    protected void initAllViewMembers(Bundle savedInstanceState) {
        et_phoneNum.clearFocus();
//        et_phoneNum.requestFocus();
        CommonUtil.showSoftKeyboard(et_phoneNum);
    }

    @Override
    public void startProgressDialog() {

    }

    @Override
    public void stopProgressDialog() {

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
        ((LoginRegisterActivity) getActivity()).changeTab(bundle);
    }

    @OnClick({
            R.id.btn_login, R.id.tv_prePhoneNum
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
                rx.Observable.timer(3, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                onSuccess();
                            }
                        });
                getPresenter().loginByPhoneNum(phoneNum);
                break;
            case R.id.tv_prePhoneNum:

                break;
        }
    }
}
