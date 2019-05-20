package com.security.app.social;

import com.security.core.constant.SecurityConstants;
import com.security.core.social.support.CoreSpringSocialConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


/**
 * app 环境下修改访问 地址
 * @author xuweizhi
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "coreSpringSocialConfigurer")) {
            CoreSpringSocialConfigurer config = (CoreSpringSocialConfigurer) bean;
            config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
            return config;
        }
        return bean;
    }

}