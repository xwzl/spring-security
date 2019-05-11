package com.java.tool.bean.controller;

import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @since 2019/05/07 9:53
 */
@Component("myImpl1")
//@Component
public class MyImpl implements My {

    @Override
    public void info() {
        System.out.println("My 接口的最终实现类！");
    }

}
