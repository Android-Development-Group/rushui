package com.pupu.rushui.net.bean;

import com.pupu.rushui.entity.BaseDO;

/**
 * Created by pupu on 2018/7/10.
 * 公共响应response
 */

public class BaseResponse<T> extends BaseDO {

    /**
     * 响应code
     */
    String response_code;

    /**
     * 响应json类型返回体，如果请求失败，返回异常原因
     */
    T response_data;

    public BaseResponse() {
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public T getResponse_data() {
        return response_data;
    }

    public void setResponse_data(T response_data) {
        this.response_data = response_data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "response_code='" + response_code + '\'' +
                ", response_data='" + response_data + '\'' +
                '}';
    }
}
