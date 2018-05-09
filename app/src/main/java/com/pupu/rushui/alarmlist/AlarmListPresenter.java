package com.pupu.rushui.alarmlist;

import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.util.DataPreference;

import java.util.List;

/**
 * Created by pupu on 2018/4/28.
 */

public class AlarmListPresenter implements AlarmListContract.Presenter {

    AlarmListContract.View view;

    @Override
    public void attachView(BaseView view) {
        this.view = (AlarmListContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void refreshData() {
        List<AlarmTime> tmpList = DataPreference.getAlarmList();
        if (tmpList != null) {
            view.refreshAlarmList(tmpList);
        }
    }
}
