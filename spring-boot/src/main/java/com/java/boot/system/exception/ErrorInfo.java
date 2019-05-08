package com.java.boot.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuweizhi
 * @date 2019/04/24 14:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo<T> implements Serializable {

    private static final long serialVersionUID = -1971078883660230552L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息实体
     */
    private String message;

    /**
     * url 路径
     */
    private String url;

    /**
     * 异常实体
     */
    private T data;

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                ", data=" + data +
                '}';
    }
}
