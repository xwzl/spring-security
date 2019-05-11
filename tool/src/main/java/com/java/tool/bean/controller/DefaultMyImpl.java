package com.java.tool.bean.controller;

import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @since 2019/05/07 9:51
 */
@Component
public class DefaultMyImpl implements My {

    @Override
    public void info() {
        System.out.println("My 接口的默认实现类!");
    }
}
