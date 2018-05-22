package com.pupu.rushui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pupu.rushui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by pulan on 18/3/14.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView<T> {

    ProgressDialog progressDialog;
    @BindView(R.id.iv_close)
    public ImageView iv_close;
    @BindView(R.id.tv_title)
    public TextView tv_title;

    protected T presenter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        ButterKnife.bind(this);
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }

        initData();
        initView();

        //极光统计
        JAnalyticsInterface.onPageStart(this, this.getClass().getCanonicalName());

    }

    protected abstract int setLayoutResourceID();

    protected abstract T getPresenter();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("正在玩命加载...");
        }
        progressDialog.show();
    }

    @Override
    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 开启新页面
     *
     * @param clazz
     */
    public void start2Activity(Class clazz) {
        Intent it = new Intent(this, clazz);
        startActivity(it);
    }

    /**
     * 开启新页面，带参数
     *
     * @param clazz
     */
    public void start2Activity(Class clazz, Bundle bundle) {
        Intent it = new Intent(this, clazz);
        if (bundle != null) {
            it.putExtras(bundle);
        }
        startActivity(it);
    }

    /**
     * 带返回结果联动的页面开启
     *
     * @param clazz
     * @param bundle
     * @param code
     */
    public void start2ActivityForResult(Class clazz, Bundle bundle, int code) {
        Intent it = new Intent(this, clazz);
        if (bundle != null) {
            it.putExtras(bundle);
        }
        startActivityForResult(it, code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
        //极光统计
        JAnalyticsInterface.onPageEnd(this, this.getClass().getCanonicalName());
    }

    @OnClick(R.id.iv_close)
    public void onBackClicked(View view) {
        finish();
    }
}
