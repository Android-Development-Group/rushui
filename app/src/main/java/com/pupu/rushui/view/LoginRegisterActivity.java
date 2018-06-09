package com.pupu.rushui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;

import com.pupu.rushui.R;
import com.pupu.rushui.adapter.CommonPagerAdapter;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.view.fragment.PhoneNumFragment;
import com.pupu.rushui.view.fragment.VerifyCodeFragment;
import com.pupu.rushui.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by pupu on 2018/5/10.
 */

public class LoginRegisterActivity extends BaseActivity {

    @BindView(R.id.vp_loginRegister)
    NoScrollViewPager vp_loginRegister;

    List<Fragment> fragments;
    CommonPagerAdapter adapter;

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
        return R.layout.activity_loginregister;
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_welcome);
        fragments = new ArrayList<>();
        fragments.add(new PhoneNumFragment());
        fragments.add(new VerifyCodeFragment());

        adapter = new CommonPagerAdapter(getSupportFragmentManager(), fragments);
        vp_loginRegister.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    /**
     * 切换fragment
     */
    public void changeTab(Bundle bundle) {
        fragments.get(1).setArguments(bundle);
        vp_loginRegister.setCurrentItem(1);
    }
}
