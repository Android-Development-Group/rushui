package com.pupu.rushui.base;

/**
 * Created by pulan on 18/3/2.
 */

public interface BasePresenter<T extends BaseView<P>, P> {

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    void attachView(T view);

    /**
     * 断开view，一般在onDestroy中调用
     */
    void detachView();
}
