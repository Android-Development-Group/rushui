package com.pupu.rushui.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.UserInfo;
import com.pupu.rushui.util.DataPreference;
import com.pupu.rushui.widget.RoundAngleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pupu on 2018/6/14.
 */

public class MineActivity extends BaseActivity {

    @BindView(R.id.tv_userName)
    TextView tv_userName;

    @BindView(R.id.riv_avatar)
    RoundAngleImageView riv_avatar;

    /**
     * 本地用户信息
     */
    UserInfo userInfo;

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
        if (userInfo != null) {
            if (TextUtils.isEmpty(userInfo.getUserName())) {
                tv_userName.setText(R.string.str_notSet);
            } else {
                tv_userName.setText(userInfo.getUserName());
            }
            Glide.with(this).load(userInfo.getAvatarUrl()).into(riv_avatar);
        }
    }

    @Override
    protected void initData() {
        userInfo = DataPreference.getUserInfo();
    }

    @OnClick({R.id.layout_mineDetail, R.id.layout_sleepData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_sleepData:
                start2Activity(SleepListActivity.class);
                break;
            case R.id.layout_mineDetail:
                start2Activity(MineDetailActivity.class);
                break;

        }
    }
}
