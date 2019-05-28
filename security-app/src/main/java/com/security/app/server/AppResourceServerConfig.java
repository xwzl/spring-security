package com.security.app.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author xuweizhi
 */
@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {

}