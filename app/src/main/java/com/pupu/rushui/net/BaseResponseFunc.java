package com.pupu.rushui.net;

import com.pupu.rushui.net.bean.BaseResponse;

import rx.functions.Func1;

/**
 * Created by pupu on 2018/7/11.
 * 响应剥离器
 */

public class BaseResponseFunc<T> implements Func1<BaseResponse<T>, T> {
    final static String SUCCESS = "200";

    @Override
    public T call(BaseResponse<T> tBaseResponse) {
        if (tBaseResponse == null ||
                !tBaseResponse.getResponse_code().equals("200")) {
            if (tBaseResponse.getResponse_data() != null &&
                    tBaseResponse.getResponse_data() instanceof String) {
                throw new ResponseError(tBaseResponse.getResponse_data().toString());
            } else {
                throw new ResponseError(ErrorCode.FAILED.getMessage());
            }
        }
        return tBaseResponse.getResponse_data();
    }
}
