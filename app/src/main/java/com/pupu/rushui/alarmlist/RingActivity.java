package com.pupu.rushui.alarmlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.common.Constant;
import com.pupu.rushui.entity.AlarmTime;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/4/17.
 */

public class RingActivity extends Activity {

    @Bind(R.id.tv_time)
    TextView tv_time;

    /**
     * 闹钟时间
     */
    AlarmTime mAlarmTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        ButterKnife.bind(this);
        setFinishOnTouchOutside(false);

        initView();

    }

    private void initView() {
        if (getIntent().getExtras() != null) {
            mAlarmTime = (AlarmTime) getIntent().getExtras().getSerializable("alarmTime");
            if(mAlarmTime!=null){
                String hour, minute;
                if (mAlarmTime.getHour24() < 10) {
                    hour = "0" + String.valueOf(mAlarmTime.getHour24());
                } else {
                    hour = String.valueOf(mAlarmTime.getHour24());
                }

                if (mAlarmTime.getMinute() < 10) {
                    minute = "0" + String.valueOf(mAlarmTime.getMinute());
                } else {
                    minute = String.valueOf(mAlarmTime.getMinute());
                }
                tv_time.setText(hour + " : " + minute);
            }
        }
    }

    @Override
    public void finish() {
        //发送关闭闹钟广播
        Intent closeRingIt = new Intent();
        closeRingIt.setAction(Constant.ALARM_CLOSE_RING);
        sendBroadcast(closeRingIt);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.btn_ring})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ring:
                finish();
                break;
            default:
                break;
        }
    }

}
