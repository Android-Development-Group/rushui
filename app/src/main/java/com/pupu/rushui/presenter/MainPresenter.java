package com.pupu.rushui.presenter;

import com.pupu.rushui.base.BaseView;
import com.pupu.rushui.contract.MainContract;
import com.pupu.rushui.datasource.RushuiDataSource;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.PhoneInfo;

import java.util.concurrent.TimeUnit;

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
        //延后3s弹出提示框
        dataSource.getPhoneInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhoneInfo>() {
                    @Override
                    public void call(PhoneInfo phoneInfo) {
                        if (phoneInfo == null || phoneInfo.isFisrtOpen() == true) {
                            if (phoneInfo == null) {
                                phoneInfo = new PhoneInfo();
                            }
                            phoneInfo.setFisrtOpen(false);
                            dataSource.setPhoneInfo(phoneInfo);
                            //延后3s弹出提示框
                            rx.Observable.timer(3, TimeUnit.SECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Long>() {
                                        @Override
                                        public void call(Long aLong) {
                                            if (view != null) {
                                                view.remindLoginOrRegister();
                                            }
                                        }
                                    });
                        }
                    }
                });
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
