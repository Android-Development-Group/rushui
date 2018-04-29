package com.pupu.rushui.result;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;

/**
 * Created by pupu on 2018/4/10.
 */

public class SleepResultActivity extends BaseActivity {
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
    }

    @Override
    protected void initData() {

    }
}
