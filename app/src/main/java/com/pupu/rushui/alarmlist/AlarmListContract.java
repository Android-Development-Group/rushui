package com.pupu.rushui.alarmlist;

import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.entity.AlarmTime;

import java.util.List;

/**
 * Created by pupu on 2018/4/28.
 */

public class AlarmListContract {

    interface View extends BaseView {
        /**
         * 获取recyclerview对应的的list
         *
         * @return
         */
        List<AlarmTime> getAlarmTimeList();
    }

    interface Presenter extends BasePresenter {
        /**
         * 刷新列表
         */
        void refreshData();
    }
}
