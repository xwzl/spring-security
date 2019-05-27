package com.security.browser.config;

import com.security.browser.session.BrowserExpiredSessionStrategy;
import com.security.browser.session.BrowserInvalidSessionStrategy;
import com.security.core.proterties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author xuweizhi
 * @date 2019/05/27 22:17
 */
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new BrowserInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new BrowserExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 退出时的处理策略配置
     */
    //@Bean
    //@ConditionalOnMissingBean(LogoutSuccessHandler.class)
    //public LogoutSuccessHandler logoutSuccessHandler(){
    //    return new ImoocLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    //}
}
