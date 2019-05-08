package com.java.boot.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 统一返回格式
 *
 * @author xuweizhi
 * @since 2019/04/24 11:03
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
public class ResultVO<T> extends BaseResultVO {

    private static final long serialVersionUID = -4324132761871372735L;

    private T data;

    @Override
    public String toString() {
        return "ResultVO{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

