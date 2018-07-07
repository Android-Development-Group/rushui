package com.pupu.rushui.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pupu.rushui.R;
import com.pupu.rushui.adapter.SleepRvAdapter;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.entity.SleepData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by pupu on 2018/7/2.
 */

public class SleepListActivity extends BaseActivity {

    /**
     * 下拉刷新
     */
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;

    /**
     * 睡眠数据列表
     */
    @BindView(R.id.rv_sleepData)
    RecyclerView rv_sleepData;

    /**
     * 睡眠数据适配器
     */
    SleepRvAdapter adapter;

    /**
     * 睡眠数据
     */
    List<SleepData> mDatas;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sleeplist;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.str_sleepData);
        layout_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新，请求网络
            }
        });
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        testData();
        adapter = new SleepRvAdapter(this, mDatas);
        rv_sleepData.setLayoutManager(new LinearLayoutManager(this));
        rv_sleepData.setItemAnimator(new DefaultItemAnimator());
        rv_sleepData.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 模拟测试数据
     */
    void testData() {
        mDatas = new ArrayList<>();
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
        mDatas.add(new SleepData());
    }
}
