package com.pupu.rushui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;

/**
 * Created by pupu on 2018/6/9.
 * 吐槽页面
 */

public class FuckAppActivity extends BaseActivity {


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
        return R.layout.activity_fuckapp;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_fuckApp);
    }

    @Override
    protected void initData() {

    }
}
