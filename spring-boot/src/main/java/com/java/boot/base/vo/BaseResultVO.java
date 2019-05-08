package com.java.boot.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基本返回实体
 *
 * @author xuweizhi
 * @since  2019/04/24 11:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResultVO implements Serializable {

    private static final long serialVersionUID = -8918332659134764288L;

    Integer code;

    String message;


    @Override
    public String toString() {
        return "BaseResultVO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
