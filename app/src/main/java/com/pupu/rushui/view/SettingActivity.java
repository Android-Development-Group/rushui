package com.pupu.rushui.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.WhiteNoise;
import com.pupu.rushui.util.CommonUtil;
import com.pupu.rushui.util.DataPreference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/3/25.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.sb_alarm)
    Switch sb_alarm;

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
                    sb_alarm.setEnabled(true);
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
            sb_alarm.setEnabled(false);
        } else {
            tv_alarm.setText(alarmTime.parseToTime());
            sb_alarm.setEnabled(true);
        }
        sb_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (alarmTime != null) {
                    if (isChecked == true) {
                        alarmTime.setOpen(true);
                        CommonUtil.showToast(R.string.str_on);
                    } else {
                        alarmTime.setOpen(false);
                        CommonUtil.showToast(R.string.str_off);
                    }
                    DataPreference.setAlarm(alarmTime);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (alarmTime != null) {
            if (alarmTime.isOpen() == true) {
                sb_alarm.setChecked(true);
            } else {
                sb_alarm.setChecked(false);
            }
        }
    }

    @Override
    protected void initData() {
        alarmTime = DataPreference.getAlarm();
    }

    @OnClick({R.id.layout_clock, R.id.layout_whiteNoise,
            R.id.layout_zaoshui, R.id.layout_about, R.id.layout_fuckApp,
            R.id.layout_markApp, R.id.layout_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_share:
                //分享图片，引流
                Intent it = new Intent();
                it.setAction(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.mipmap.img_share));
                it.setType("image/jpeg");
//                it.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
//                it.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ好友或QQ群
//                it.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片
                startActivity(Intent.createChooser(it, "繁杂世界，简单入睡"));
                break;
            case R.id.layout_markApp:
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    CommonUtil.showToast(R.string.str_noMarket);
                }
                break;
            case R.id.layout_fuckApp:
                start2Activity(FuckAppActivity.class);
                break;
            case R.id.layout_clock:
                Bundle alarmBundle = new Bundle();
                if (alarmTime != null) {
                    alarmBundle.putSerializable("alarmTime", alarmTime);
                }
                start2ActivityForResult(AlarmDetailActivity.class, alarmBundle, CODE_ALARM_DETAIL);
                break;
            case R.id.layout_whiteNoise:
                start2Activity(WhiteNoiseActivity.class);
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
