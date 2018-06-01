package com.pupu.rushui.view;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.contract.LoginContract;
import com.pupu.rushui.presenter.LoginPresenter;
import com.pupu.rushui.util.CommonUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/6/1.
 */

public class VerifyCodeActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.btn_request)
    Button btn_request;
    @BindView(R.id.tv_remind)
    TextView tv_remind;
    @BindView(R.id.et_code)
    EditText et_code;

    String phoneNum;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_verifycode;
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
        tv_title.setText(R.string.str_verifyCode);
        if (!TextUtils.isEmpty(phoneNum)) {
            tv_remind.setText(String.format(getString(R.string.str_remindVerifyPhone), phoneNum));
        }
    }

    @Override
    protected void initData() {
        phoneNum = getIntent().getExtras().getString("phoneNum");
    }


    @Override
    public void onFailed(String msg) {
        CommonUtil.showToast(msg);
        btn_request.setEnabled(true);
        btn_request.setText(R.string.str_retry);
    }

    @Override
    public void onSuccess() {
        //跳转主页
        start2Activity(MainActivity.class);
        finish();
    }

    @OnClick({
            R.id.btn_request, R.id.tv_passwordLogin
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_passwordLogin:
                break;
            case R.id.btn_request:
                String code = et_code.getText().toString().trim();
                if (TextUtils.isEmpty(code) || code.length() != 6) {
                    CommonUtil.showToast(R.string.toast_code_err);
                    return;
                }
                btn_request.setEnabled(false);
                getPresenter().verifyCode(phoneNum, code);
                //开启倒计时
                new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(final long millisUntilFinished) {
                        Observable.create(new Observable.OnSubscribe<Long>() {
                            @Override
                            public void call(Subscriber<? super Long> subscriber) {
                                subscriber.onNext(millisUntilFinished / 1000);
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {
                                        btn_request.setText(String.format(getString(R.string.str_remindVerify), aLong.intValue()));
                                    }
                                });
                    }

                    @Override
                    public void onFinish() {
                        Observable.timer(0, TimeUnit.SECONDS)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {
                                        btn_request.setEnabled(true);
                                        btn_request.setText(R.string.str_retry);
                                    }
                                });
                    }
                }.start();
                break;
        }
    }
}
