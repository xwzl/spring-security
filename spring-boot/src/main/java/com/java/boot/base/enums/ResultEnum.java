package com.java.boot.base.enums;

import org.jetbrains.annotations.Contract;

/**
 * @author xuweizhi
 * @since  2019/04/24 14:36
 */
public enum ResultEnum {

    /**
     * 请求成功
     */
    SUCCESS(0, "请求成功"),
    /**
     * 请求错误
     */
    FAILURE(-1, "出现异常");


    private Integer code;

    private String message;

    @Contract(pure = true)
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Contract(pure = true)
    public Integer getCode() {
        return code;
    }

    @Contract(pure = true)

    public String getMessage() {
        return message;
    }
}
