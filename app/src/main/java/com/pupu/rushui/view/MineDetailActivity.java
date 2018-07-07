package com.pupu.rushui.view;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.util.DataPreference;
import com.pupu.rushui.util.Logger;
import com.pupu.rushui.widget.BirthView;
import com.pupu.rushui.widget.LoadingButton;
import com.pupu.rushui.widget.RulerView;
import com.pupu.rushui.widget.SexSwitch;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/6/14.
 */

public class MineDetailActivity extends BaseActivity {
    final static String TAG = MineDetailActivity.class.getName();

    @BindView(R.id.ruler_height)
    RulerView ruler_height;
    @BindView(R.id.ruler_weight)
    RulerView ruler_weight;
    @BindView(R.id.et_userName)
    EditText et_userName;
    @BindView(R.id.tv_birth)
    TextView tv_birth;
    @BindView(R.id.tv_height)
    TextView tv_height;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.birthView)
    BirthView birthView;

    /**
     * 確定按鈕
     */
    @BindView(R.id.btn_ok)
    LoadingButton btn_ok;

    /**
     * 性别选择器
     */
    @BindView(R.id.sex_switch)
    SexSwitch sex_switch;

    UserInfo userInfo;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_mine_detail;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_mine);
        sex_switch.setOnCheckedChangeListener(new SexSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(boolean flag) {
                if (flag == true) {
                    //woman

                } else {
                    //man

                }
            }
        });
        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(final float value) {
                Logger.i(TAG, "ruler_height value===>" + value);
                Observable.timer(0, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                tv_height.setText(String.format(getString(R.string.str_height_cm), value + ""));
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                            }
                        });
            }
        });
        ruler_height.setValue(172, 80, 250, 1);
        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(final float value) {
                Logger.i(TAG, "ruler_weight value===>" + value);
                Observable.timer(0, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                tv_weight.setText(String.format(getString(R.string.str_weight_kg), value + ""));
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                            }
                        });
            }
        });
        ruler_weight.setValue(60, 35, 120, 1);
        birthView.setOnDateChangedListener(new BirthView.OnDateChangedListener() {
            @Override
            public void onDateChanged(final String yearSel, final String monthSel, final String daySel) {
                Observable.timer(0, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                tv_birth.setText(String.format(getString(R.string.str_birth), yearSel, monthSel, daySel));
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
            }
        });
        tv_birth.setText(String.format(getString(R.string.str_birth), "1994", "10", "17"));
        tv_height.setText(String.format(getString(R.string.str_height_cm), "172"));
        tv_weight.setText(String.format(getString(R.string.str_weight_kg), "60"));

        if (TextUtils.isEmpty(userInfo.getUserName())) {
            et_userName.setText(getString(R.string.str_notSet));
        } else {
            et_userName.setText(userInfo.getUserName());
        }

    }

    @Override
    protected void initData() {
        //获取本地用户信息
        userInfo = DataPreference.getUserInfo();
    }

    /**
     * 保存个人信息
     *
     * @param view
     */
    @OnClick(R.id.btn_ok)
    public void onOkClicked(View view) {
        //请求网络
        btn_ok.startLoading();
    }
}
