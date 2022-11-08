package com.sun.mall.common.exception;

/**
 * @author sun
 */

public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(50000, "系统异常"),
    VALID_EXCEPTION(50001, "参数格式校验失败");

    private Integer code;
    private String msg;

    BizCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
