package com.pupu.rushui.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.util.DataPreference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/3/25.
 */

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_alarm)
    TextView tv_alarm;
    AlarmTime alarmTime;
    final static int CODE_ALARM_DETAIL = 0X001;

    final static int MSG_UPDATE_ALARM = 0x1001;//更新闹钟时间
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_ALARM:
                    tv_alarm.setText(alarmTime.parseToTime());
                    break;
                default:
                    break;
            }
        }
    };

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
        return R.layout.activity_setting;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_setting);
        if (alarmTime == null) {
            tv_alarm.setText(getString(R.string.str_notSet));
        } else {
            tv_alarm.setText(alarmTime.parseToTime());
        }
    }

    @Override
    protected void initData() {
        alarmTime = DataPreference.getAlarm();
    }

    @OnClick({R.id.layout_clock, R.id.layout_whiteNoise,
            R.id.layout_zaoshui, R.id.layout_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_clock:
                Bundle alarmBundle = new Bundle();
                if (alarmTime != null) {
                    alarmBundle.putSerializable("alarmTime", alarmTime);
                }
                start2ActivityForResult(AlarmDetailActivity.class, alarmBundle, CODE_ALARM_DETAIL);
                break;
            case R.id.layout_whiteNoise:

                break;
            case R.id.layout_about:
                start2Activity(AboutActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_ALARM_DETAIL) {
            if (data != null && data.getExtras() != null && data.getExtras().getSerializable("alarmTime") != null) {
                alarmTime = (AlarmTime) data.getExtras().getSerializable("alarmTime");
                handler.sendEmptyMessage(MSG_UPDATE_ALARM);
            }
        }
    }
}
