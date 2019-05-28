package com.security.core;

import com.security.core.proterties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 让 SecurityProperties 配置其效果
 *
 * @author xuweizhi
 * @date 2019/05/15 1:07
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    /**
     * 注入加密解密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 其默认实现
        return new BCryptPasswordEncoder();
    }

}
