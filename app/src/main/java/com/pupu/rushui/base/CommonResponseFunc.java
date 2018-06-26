package com.pupu.rushui.base;

import android.text.TextUtils;

import com.pupu.rushui.net.ResponseError;

import rx.functions.Func1;

/**
 * Created by pupu on 2018/6/20.
 */

public class CommonResponseFunc<T> implements Func1<CommonResponse<T>, T> {

    @Override
    public T call(CommonResponse<T> response) {
        if (!response.getResponse_code().equals("200")) {
            if (response.getResponse_data() != null &&
                    response.getResponse_data() instanceof String) {
                if (!TextUtils.isEmpty((String) response.getResponse_data())) {
                    throw new ResponseError((String) response.getResponse_data());
                }
            }
        }
        if (response.getResponse_data() == null) {
            throw new ResponseError("服务器错误!");
        }
        return response.getResponse_data();
    }
}
