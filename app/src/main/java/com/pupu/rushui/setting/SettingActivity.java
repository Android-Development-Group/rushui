package com.pupu.rushui.setting;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.alarmlist.AlarmListActivity;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.util.CommonUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/3/25.
 */

public class SettingActivity extends BaseActivity {


    @Bind(R.id.tv_version)
    TextView tv_version;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_setting;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_setting);
        try {
            tv_version.setText(CommonUtil.getAppVersion(getPackageName()) + "." + CommonUtil.getVersionCode(this.getApplicationContext()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.layout_version, R.id.layout_clock, R.id.layout_whiteNoise,
            R.id.layout_zaoshui, R.id.layout_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_clock:
                start2Activity(AlarmListActivity.class);
                break;
            default:
                break;
        }
    }
}
