package com.pupu.rushui.view;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.SleepData;
import com.pupu.rushui.util.TimeUtil;

import java.text.Format;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/4/10.
 */

public class SleepResultActivity extends BaseActivity {

    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_endTime)
    TextView tv_endTime;
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    @BindView(R.id.fab_share)
    FloatingActionButton fab_share;

    /**
     * 睡眠数据
     */
    SleepData sleepData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sleep_result;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_sleepResult);
        //24h制
        int startHour = sleepData.getStartTime().getHours();
        int startMin = sleepData.getStartTime().getMinutes();
        AlarmTime startTime = new AlarmTime();
        startTime.setHour24(startHour);
        startTime.setMinute(startMin);
        tv_startTime.setText(startTime.parseToTime());

        int endHour = sleepData.getEndTime().getHours();
        int endMin = sleepData.getEndTime().getMinutes();
        AlarmTime endTime = new AlarmTime();
        endTime.setHour24(startHour);
        endTime.setMinute(startMin);
        tv_endTime.setText(endTime.parseToTime());

        //计算睡眠时长
        tv_duration.setText(
                String.format(getString(R.string.str_sleepDuration), "" + TimeUtil.caculateDuration(startTime, endTime)[0], "" + TimeUtil.caculateDuration(startTime, endTime)[1])
        );

    }

    @Override
    protected void initData() {
        sleepData = (SleepData) getIntent().getSerializableExtra("sleepData");
    }

    @OnClick({
            R.id.fab_share
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_addAlarm:
                //分享

                break;
        }
    }
}
