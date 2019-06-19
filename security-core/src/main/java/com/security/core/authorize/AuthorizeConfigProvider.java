package com.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 默认的授权配置管理器
 *
 * @author xuweizhi
 * @date 2019/06/20 0:03
 */

public interface AuthorizeConfigProvider {

    /**
     * 自定义权限表达式
     *
     * @param config {@link HttpSecurity#authorizeRequests} 方法的返回值
     * @return 返回的boolean表示配置中是否有针对anyRequest的配置。在整个授权配置中，
     * 应该有且仅有一个针对anyRequest的配置，如果所有的实现都没有针对anyRequest的配置，
     * 系统会自动增加一个anyRequest().authenticated()的配置。如果有多个针对anyRequest
     * 的配置，则会抛出异常。
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
