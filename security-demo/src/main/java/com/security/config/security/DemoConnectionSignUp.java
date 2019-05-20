package com.security.config.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.stereotype.Component;

/**
 * 用于插入数据认证表，作为唯一标识
 *
 * {@link JdbcUsersConnectionRepository}
 * @author xuweizhi
 * @date 2019/05/16 18:31
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        // 根据社交用户信息默认创建用户并返回用户唯一标志
        return connection.getDisplayName();
    }

}
