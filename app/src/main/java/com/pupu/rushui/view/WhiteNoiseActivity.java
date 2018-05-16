package com.pupu.rushui.view;

import android.support.v7.widget.RecyclerView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;

import butterknife.Bind;

/**
 * Created by pupu on 2018/5/13.
 */

public class WhiteNoiseActivity extends BaseActivity {

    @Bind(R.id.rv_whiteNoise)
    RecyclerView rv_whiteNoise;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_whitenoise;
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

    }
}
