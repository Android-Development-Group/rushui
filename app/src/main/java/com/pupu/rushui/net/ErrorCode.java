package com.pupu.rushui.net;

/**
 * Created by pupu on 2018/7/11.
 */

public enum ErrorCode {


    /**
     * 1xx - 5xx 业务逻辑错误
     * <p>
     * 8xx-9xx 服务器交互信息错误
     * 002-100 系统级别错误
     */


    SUCCEED("200", "成功"),
    FAILED("-1", "┗|｀O′|┛ 嗷~~服务器开了小差"),
    DENY("-2", "请求拒绝");

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据错误编码查找ResultCode对象
     *
     * @param code
     * @return
     */
    public static ErrorCode find(int code) {

        for (ErrorCode ec : ErrorCode.values()) {
            if (ec.code.equals(code)) {
                return ec;
            }
        }
        return null;
    }
    }