package com.security.app.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author xuweizhi
 * @since 2019/06/09 21:30
 */
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 解决 AuthenticationManager 无法注入问题
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
