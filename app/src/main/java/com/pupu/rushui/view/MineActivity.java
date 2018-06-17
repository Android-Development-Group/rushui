package com.pupu.rushui.view;

import android.view.View;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;

import butterknife.OnClick;

/**
 * Created by pupu on 2018/6/14.
 */

public class MineActivity extends BaseActivity {

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_mine;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_mine);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.layout_mineDetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_mineDetail:
                start2Activity(MineDetailActivity.class);
                break;
        }
    }
}
