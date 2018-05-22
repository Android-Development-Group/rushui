package com.pupu.rushui.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.pupu.rushui.R;
import com.pupu.rushui.app.MyApplication;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.util.AlarmManagerUtil;
import com.pupu.rushui.util.DataPreference;
import com.pupu.rushui.util.Logger;
import com.pupu.rushui.widget.TimeDiskView;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/4/12.
 */

public class AlarmDetailActivity extends BaseActivity {

    private static final String TAG = AlarmDetailActivity.class.getName();
    @BindView(R.id.tdv)
    TimeDiskView tdv;
    AlarmTime alarmTime;
    @BindView(R.id.tv_remind)
    TextView tv_remind;
    /**
     * 系统当前时间
     */
    AlarmTime curTime;

    final static int MSG_UPDATE_REMIND = 0x1001;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_REMIND:
                    int deta = 0;
                    if (curTime.getHour24() > alarmTime.getHour24()) {
                        deta = 23 - curTime.getHour24() + alarmTime.getHour24() + 1;
                    } else {
                        deta = alarmTime.getHour24() - curTime.getHour24();
                    }
                    tv_remind.setText(String.format(getString(R.string.str_alarmRemind),
                            deta));
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
        return R.layout.activity_alarm_detail;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tdv.setOnTimeChangedListener(new TimeDiskView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(boolean apm, int hour, int minute, int second) {
                alarmTime.setHour24(hour);
                alarmTime.setMinute(minute);
                handler.sendEmptyMessage(MSG_UPDATE_REMIND);
            }
        });
        if (alarmTime == null) {
            tdv.showTime(7, 30, 0);
            alarmTime = new AlarmTime();
            alarmTime.setHour24(7);
            alarmTime.setMinute(30);
        } else {
            tdv.showTime(alarmTime.getHour24(), alarmTime.getMinute(), 0);
        }
        handler.sendEmptyMessage(MSG_UPDATE_REMIND);
    }

    @Override
    protected void initData() {
        alarmTime = (AlarmTime) getIntent().getExtras().getSerializable("alarmTime");
        curTime = new AlarmTime();
        curTime.setHour24(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        );

    }

    @OnClick({R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                //添加闹钟
                alarmTime.setHour24(tdv.getHour24());
                alarmTime.setHour(tdv.getHour());
                alarmTime.setMinute(tdv.getMinute());
                alarmTime.setOpen(true);
                Logger.i(TAG, "设置闹钟：" + alarmTime.toString());
                DataPreference.setAlarm(alarmTime);
                AlarmManagerUtil.setAlarm(MyApplication.getInstance(), 1,
                        alarmTime,
                        alarmTime.getHour24(), alarmTime.getMinute(),
                        alarmTime.getId(), 0, "", 1);
                Intent it = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("alarmTime", alarmTime);
                it.putExtras(bundle);
                setResult(RESULT_OK, it);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
