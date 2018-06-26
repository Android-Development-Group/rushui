package com.pupu.rushui.base;

/**
 * Created by pupu on 2018/6/20.
 * 公共响应
 */

public class CommonResponse<T> {
    /**
     * 响应code
     */
    String response_code;

    /**
     * 响应实体
     */
    T response_data;

    public CommonResponse() {
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
        return "CommonResponse{" +
                "response_code='" + response_code + '\'' +
                ", response_data=" + response_data +
                '}';
    }
}
