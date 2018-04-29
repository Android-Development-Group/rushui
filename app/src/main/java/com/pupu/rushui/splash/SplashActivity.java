package com.pupu.rushui.splash;

import android.os.Handler;
import android.os.Message;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.home.MainActivity;


/**
 * Created by pupu on 2018/4/7.
 */

public class SplashActivity extends BaseActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                default:
                    start2Activity(MainActivity.class);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        handler.sendEmptyMessageDelayed(0x001, 500);
    }
}
