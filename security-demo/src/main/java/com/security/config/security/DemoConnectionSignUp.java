package com.security.config.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 用于插入数据认证表，作为唯一标识
 * @author xuweizhi
 * @date 2019/05/16 18:31
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        // 以用户作为唯一标识，插入数据
        return connection.getDisplayName();
    }

}
