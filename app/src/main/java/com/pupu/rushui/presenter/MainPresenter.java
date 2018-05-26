package com.pupu.rushui.presenter;

import android.os.Handler;
import android.os.Message;

import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.contract.MainContract;
import com.pupu.rushui.datasource.RushuiDataSource;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.service.SleepService;

import java.util.Observable;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pupu on 2018/4/7.
 */

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;

    RushuiDataSource dataSource;

    public MainPresenter() {
        dataSource = new RushuiDataSource();
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (MainContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void preSleep() {
        view.preSleep();
    }

    @Override
    public void startSleep() {
        dataSource.getAlarm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<AlarmTime>() {
                    @Override
                    public void call(AlarmTime alarmTime) {
                        view.startSleep(alarmTime);
                    }
                });
    }

    @Override
    public void stopSleep() {
        view.stopSleep();
    }
}
