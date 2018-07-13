package com.pupu.rushui.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.net.ApiClient;
import com.pupu.rushui.net.BaseResponseFunc;
import com.pupu.rushui.net.bean.BaseResponse;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.util.DataPreference;
import com.pupu.rushui.widget.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/6/9.
 * 吐槽页面
 */

public class FuckAppActivity extends BaseActivity {

    @BindView(R.id.et_fuckApp)
    EditText et_fuckApp;

    @BindView(R.id.btn_ok)
    LoadingButton btn_ok;

    /**
     * 本地用户信息
     */
    UserInfo userInfo;

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
        return R.layout.activity_fuckapp;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_fuckApp);
    }

    @Override
    protected void initData() {
        userInfo = DataPreference.getUserInfo();
    }

    @OnClick({
            R.id.btn_ok
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (TextUtils.isEmpty(et_fuckApp.getText().toString())) {
                    CommonUtil.showToast(R.string.str_say_something);
                    return;
                }
                btn_ok.setEnabled(false);
                btn_ok.startLoading();
                //请求网络
                ApiClient.getInstance().getApi().requestFeedBack(
                        userInfo == null ? 0 : userInfo.getUserId(), Build.BRAND + ":" + Build.MODEL, et_fuckApp.getText().toString()
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new BaseResponseFunc<String>())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                onFeedbackSuccess();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                                //请求失败
                                onFeedbackFailed(getString(R.string.toast_server_err));
                            }
                        });
                break;
        }
    }

    /**
     * 反馈成功
     */
    void onFeedbackSuccess() {
        CommonUtil.showToast(R.string.str_ok);
        finish();
    }

    /**
     * 反馈失败
     *
     * @param msg 失败原因
     */
    void onFeedbackFailed(String msg) {
        CommonUtil.showToast(msg);
    }
}
