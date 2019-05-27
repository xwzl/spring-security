package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;

/**
 * @author xuweizhi
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig {

    @Bean
    public HttpSessionSessionStrategy httpSessionStrategy() {
        return new HttpSessionSessionStrategy();
    }

}
