package com.data.dynamic.enums;

import lombok.Getter;

/**
 * @author xuweizhi
 * @since 2019/06/04 14:42
 */
@Getter
public enum DataSourceKey {

    /**
     * master
     */
    master("master"),
    slaveAlpha("slaveAlpha"),
    slaveBeta("slaveBeta"),
    slaveGamma("slaveGamma");

    private String source;

    private DataSourceKey(String source) {
        this.source = source;
    }
}
