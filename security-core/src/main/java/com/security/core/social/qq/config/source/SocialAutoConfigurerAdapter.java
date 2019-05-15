package com.security.core.social.qq.config.source;

import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * SpringBoot 2.0 被移除，尴尬得一匹
 *
 * @author xuweizhi
 */
public abstract class SocialAutoConfigurerAdapter extends SocialConfigurerAdapter {
    public SocialAutoConfigurerAdapter() {
    }
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(this.createConnectionFactory());
    }
    protected abstract ConnectionFactory<?> createConnectionFactory();
}