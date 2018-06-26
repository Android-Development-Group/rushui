package com.pupu.rushui.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.pupu.rushui.R;
import com.pupu.rushui.adapter.WhiteNoiseRvAdapter;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.base.BasePresenter;
import com.pupu.rushui.base.CommonResponseFunc;
import com.pupu.rushui.entity.WhiteNoise;
import com.pupu.rushui.net.ApiClient;
import com.pupu.rushui.util.DataPreference;
import com.pupu.rushui.util.PlayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by pupu on 2018/5/13.
 */

public class WhiteNoiseActivity extends BaseActivity {

    @BindView(R.id.rv_whiteNoise)
    RecyclerView rv_whiteNoise;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    WhiteNoiseRvAdapter adapter;
    List<WhiteNoise> mDatas;

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
        adapter.notifyDataSetChanged();
        tv_title.setText(R.string.str_whiteNoise);

        layout_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //请求服务器刷新列表
//                requestNet();
                //设置列表不可点击
                rv_whiteNoise.setEnabled(false);
            }
        });
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        adapter = new WhiteNoiseRvAdapter(this, mDatas);
        rv_whiteNoise.setLayoutManager(new LinearLayoutManager(this));
        rv_whiteNoise.setAdapter(adapter);
        //获取本地白噪声列表
        List<WhiteNoise> whiteNoiseList = DataPreference.getWhiteNoiseList();
        if (whiteNoiseList == null) {
            //请求网络
            localDataTest();
//            requestNet();
        } else {
            //显示
            mDatas.addAll(whiteNoiseList);
        }

        adapter.setOnItemClickListener(new WhiteNoiseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDatas.get(position).getState() == WhiteNoise.STATE_NO_CHECKED) {
                    //其它的选项需要设置成未选中
                    for (WhiteNoise whiteNoise : mDatas) {
                        whiteNoise.setState(WhiteNoise.STATE_NO_CHECKED);
                    }
                    mDatas.get(position).setState(WhiteNoise.STATE_CHECKED);
                    //播放该条助眠声
                    if (!TextUtils.isEmpty(
                            mDatas.get(position).getLocalUrl()
                    )) {
                        PlayUtils.getInstance().loadVoiceUrl(mDatas.get(position).getLocalUrl())
                                .startWithFade();
                    }
                    //设置保存本地
                    DataPreference.setWhiteNoiseList(mDatas);
                } else if (mDatas.get(position).getState() == WhiteNoise.STATE_CHECKED) {

                } else if (mDatas.get(position).getState() == WhiteNoise.STATE_NO_DOWNLOADED) {
                    //去下载
//                    downloadVoice(mDatas.get(position).getUrl());
                    mDatas.get(position).setState(WhiteNoise.STATE_DOWNLOADING);
                }
                //刷新列表
                Observable.timer(0, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                adapter.notifyDataSetChanged();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
            }
        });
    }

    /**
     * 本地测试数据生成
     */
    private void localDataTest() {
        mDatas.add(new WhiteNoise("篝火"));
        mDatas.add(new WhiteNoise("雨点"));
        mDatas.add(new WhiteNoise("ASMR"));
        mDatas.add(new WhiteNoise("悄悄话"));
        adapter.notifyDataSetChanged();
    }

    /**
     * 请求网络
     */
    private void requestNet() {
        ApiClient.getInstance().getApi()
                .getWhiteNoiseList(666)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new CommonResponseFunc<List<WhiteNoise>>())
                .subscribe(new Action1<List<WhiteNoise>>() {
                    @Override
                    public void call(List<WhiteNoise> whiteNoises) {
                        mDatas.clear();
                        mDatas.addAll(whiteNoises);
                        adapter.notifyDataSetChanged();
                        layout_refresh.setRefreshing(false);
                        rv_whiteNoise.setEnabled(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    /**
     * 下载助眠声
     *
     * @param url 网络地址
     */
    private void downloadVoice(String url) {
        if (url == null) {
            return;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayUtils.getInstance().stopWithFade();
    }
}
