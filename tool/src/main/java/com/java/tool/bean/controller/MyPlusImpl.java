package com.java.tool.bean.controller;

import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @since 2019/05/07 9:52
 */
@Component
public class MyPlusImpl implements My {

    @Override
    public void info() {
        System.out.println("My 接口的增强实现类");
    }

}
