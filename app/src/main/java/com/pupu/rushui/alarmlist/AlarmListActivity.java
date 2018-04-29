package com.pupu.rushui.alarmlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pupu.rushui.R;
import com.pupu.rushui.adapter.AlarmRvAdapter;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.util.DataPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/4/12.
 */

public class AlarmListActivity extends BaseActivity implements AlarmListContract.View {
    @Bind(R.id.rv_alarmList)
    RecyclerView rv_alarmList;
    AlarmRvAdapter adapter;
    List<AlarmTime> alarmList;

    final static int CODE_ALARM_LIST = 0x01;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_alarm_list;
    }

    @Override
    protected AlarmListContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new AlarmListPresenter();
        }
        return (AlarmListContract.Presenter) presenter;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_alarmClock);
    }

    @Override
    protected void initData() {
        alarmList = DataPreference.getAlarmList();
        if (alarmList == null) {
            alarmList = new ArrayList<>();
        }
        adapter = new AlarmRvAdapter(this, alarmList);
        rv_alarmList.setLayoutManager(new LinearLayoutManager(this));
        rv_alarmList.setAdapter(adapter);

        adapter.setOnItemClickListener(new AlarmRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("alarmTime", alarmList.get(pos));
                start2ActivityForResult(AlarmDetailActivity.class, bundle, CODE_ALARM_LIST);
            }
        });
    }

    @OnClick({R.id.fab_addAlarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_addAlarm:
                Bundle addBundle = new Bundle();
                addBundle.putSerializable("alarmTime", null);
                start2ActivityForResult(AlarmDetailActivity.class, addBundle, CODE_ALARM_LIST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_ALARM_LIST) {
            if (data != null && data.getExtras() != null && data.getExtras().getSerializable("alarmTime") != null) {
                AlarmTime alarmTime = (AlarmTime) data.getExtras().getSerializable("alarmTime");
                if (alarmTime.getId() > alarmList.size()) {
                    alarmList.add(alarmTime);
                } else {
                    alarmList.set(alarmTime.getId() - 1, alarmTime);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public List<AlarmTime> getAlarmTimeList() {
        return alarmList;
    }
}
