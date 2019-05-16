package com.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 默认的授权配置管理器
 *
 * @author xuweizhi
 * @date 2019/05/16 11:32
 */
public class DefaultAuthorizeConfigManager implements AuthorizeConfigManager {


    //@Autowired
    //private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //boolean existAnyRequestConfig = false;
        //String existAnyRequestConfigName = null;
        //
        //for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
        //    boolean currentIsAnyRequestConfig = authorizeConfigProvider.config(config);
        //    if (existAnyRequestConfig && currentIsAnyRequestConfig) {
        //        throw new RuntimeException("重复的anyRequest配置:" + existAnyRequestConfigName + ","
        //                + authorizeConfigProvider.getClass().getSimpleName());
        //    } else if (currentIsAnyRequestConfig) {
        //        existAnyRequestConfig = true;
        //        existAnyRequestConfigName = authorizeConfigProvider.getClass().getSimpleName();
        //    }
        //}
        //
        //if(!existAnyRequestConfig){
        //    config.anyRequest().authenticated();
        //}
    }

}
