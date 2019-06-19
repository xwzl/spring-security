package com.security.config.security;

import com.security.core.authorize.AuthorizeConfigManager;
import com.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * {@link AuthorizeConfigManager#config(ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)}
 * 会自动 注入到上面的配置项中
 *
 * @author xuweizhi
 * @date 2019/06/20 0:34
 */
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                "favicon.ico",
                "/user/regist"
        ).permitAll()
                .antMatchers("/user").hasRole("ADMIN");
        return false;
    }
}
