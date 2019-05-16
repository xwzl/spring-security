package com.security.core.authentication.browser;

import com.security.core.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import static com.security.core.constant.SecurityConstants.DEFAULT_UNAUTHENTICATION_URL;

/**
 * Form 表单验证的相关配置
 *
 * @author xuweizhi
 * @date 2019/05/16 11:07
 */
@Component
public class FormAuthenticationConfig {

    @Autowired
    private AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler defaultAuthenticationFailureHandler;

    public void configure(HttpSecurity http) throws Exception {
        // form 表单登录进行身份认证，所有的请求都需要身份认证才能访问
        http.formLogin()
                // 登录等浏览器的验证逻辑，如果没有 token ，就跳转登录页面进行验证，处理的URL地址为
                .loginPage(DEFAULT_UNAUTHENTICATION_URL)
                // 请求登陆处理地址,登录的 url 不用特意写controller，直接嗲用拦截器链进行拦截
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                // 表单登录成功后的 handler 处理器
                .successHandler(defaultAuthenticationSuccessHandler)
                // 失败处理
                .failureHandler(defaultAuthenticationFailureHandler);

    }

}
