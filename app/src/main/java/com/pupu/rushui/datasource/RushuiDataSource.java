package com.pupu.rushui.datasource;

import com.pupu.rushui.base.DataSource;
import com.pupu.rushui.entity.AlarmTime;
import com.pupu.rushui.entity.PhoneInfo;
import com.pupu.rushui.util.DataPreference;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by pupu on 2018/5/26.
 */

public class RushuiDataSource implements DataSource {

    public RushuiDataSource() {
    }

    @Override
    public void setAlarm(AlarmTime alarmTime) {
        if (alarmTime == null) {
            return;
        }
        DataPreference.setAlarm(alarmTime);
    }

    @Override
    public Observable<AlarmTime> getAlarm() {
        return Observable.create(new Observable.OnSubscribe<AlarmTime>() {
            @Override
            public void call(Subscriber<? super AlarmTime> subscriber) {
                AlarmTime alarmTime = DataPreference.getAlarm();
                subscriber.onNext(alarmTime);
            }
        });
    }

    @Override
    public Observable<PhoneInfo> getPhoneInfo() {
        return Observable.create(new Observable.OnSubscribe<PhoneInfo>() {
            @Override
            public void call(Subscriber<? super PhoneInfo> subscriber) {
                PhoneInfo info = DataPreference.getPhoneInfo();
                subscriber.onNext(info);
            }
        });
    }

    @Override
    public void setPhoneInfo(PhoneInfo phoneInfo) {
        if (phoneInfo == null) {
            return;
        }
        DataPreference.setPhoneInfo(phoneInfo);
    }

}
