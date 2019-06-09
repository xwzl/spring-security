package com.security.app.social;

import com.security.core.social.support.CoreSpringSocialConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


/**
 * app 环境下修改访问 地址,Spring Bean 初始化之前都会经过这两个方法
 * @author xuweizhi
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * coreSecuritySocialConfig 初始化 bean 后，改变其值。
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "coreSecuritySocialConfig")) {
            CoreSpringSocialConfigurer config = (CoreSpringSocialConfigurer) bean;
            //config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
            config.signupUrl("/social/signUp");
            return config;
        }
        return bean;
    }

}