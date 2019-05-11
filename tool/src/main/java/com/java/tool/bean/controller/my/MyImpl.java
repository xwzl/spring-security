package com.java.tool.bean.controller.my;

import com.java.tool.bean.controller.My;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @since 2019/05/07 10:54
 */
//@Component("myImpl1")
@Component
public class MyImpl implements My {

    @Override
    public void info() {
        System.out.println("My =====！");
    }

}
