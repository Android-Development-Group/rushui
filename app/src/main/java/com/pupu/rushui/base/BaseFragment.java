package com.pupu.rushui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by pupu on 2017/9/22.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected Context context;
    protected View mRootView;

    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, mRootView);//绑定framgent
        this.context = getActivity();
        initAllViewMembers(savedInstanceState);
        return mRootView;
    }

    protected abstract T getPresenter();

    public abstract int getContentViewId();

    protected abstract void initAllViewMembers(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
