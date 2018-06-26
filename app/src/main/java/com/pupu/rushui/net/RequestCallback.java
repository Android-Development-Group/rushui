package com.pupu.rushui.net;

import com.pupu.rushui.R;
import com.pupu.rushui.util.CommonUtil;
import rx.Subscriber;

public abstract class RequestCallback<T> extends Subscriber<T> {
    private static final String TAG = "RequestCallback";

    @Override
    public final void onNext(T t) {
        onResponse(t);
    }

    @Override
    public final void onError(Throwable throwable) {
        if (throwable instanceof ResponseError) {
            onFailure((ResponseError) throwable);
        } else {

        }
    }

    @Override
    public void onStart() {

    }

    public void onResponse(T response) {
    }

    public void onFailure(ResponseError error) {
        CommonUtil.showToast(R.string.toast_server_err);
    }

    @Override
    public void onCompleted() {
    }
}
