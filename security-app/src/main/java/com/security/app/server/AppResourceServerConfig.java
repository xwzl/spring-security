package com.security.app.server;

import com.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.security.core.authentication.browser.FormAuthenticationConfig;
import com.security.core.authentication.mobile.SmsCodeAuthenticationConfig;
import com.security.core.authorize.AuthorizeConfigManager;
import com.security.core.constant.SecurityConstants;
import com.security.core.proterties.SecurityProperties;
import com.security.core.validate.core.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

import static com.security.core.constant.SecurityConstants.*;

/**
 * 标注为资源服务器
 *
 * @author xuweizhi
 */
@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetailsService;

    /**
     * 表单登录配置
     */
    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    /**
     * 验证码拦截配置
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 短信验证码配置
     */
    @Autowired
    private SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;

    @Autowired
    private SpringSocialConfigurer coreSecuritySocialConfig;

    @Autowired
    private AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler defaultAuthenticationFailureHandler;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    /**
     * 配置token 缓存
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }


    /**
     * http 的验证开始
     *
     * @param http 过滤 http 请求
     */
    @Override
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

        // 验证码认证安全配置
        http.apply(validateCodeSecurityConfig)
                .and()
                // 应用短信验证码认证安全配置
                .apply(smsCodeAuthenticationConfig)
                .and()
                .apply(coreSecuritySocialConfig)
                .and()
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                // 判断之前的过滤配置，进行授权
                //.authorizeRequests()
                //// 以下路径不需要进行身份认证,securityProperties.getBrowser().getLoginPage()是真正的登录页面，包括用户自定义的
                //.antMatchers(
                //        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                //        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                //        SecurityConstants.STATIC_RESOURCES_URL,
                //        SecurityConstants.DEFAULT_SESSION_INVALID_URL,
                //        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                //        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
                //        securityProperties.getBrowser().getLoginPage(),
                //        // 配置 qq 授权后的注册页面
                //        securityProperties.getBrowser().getSignUrl(),
                //        securityProperties.getBrowser().getSignOutUrl() == null ? "404" : securityProperties.getBrowser().getSignOutUrl(),
                //        "favicon.ico",
                //        "/user/regist",
                //        "/oauth/authorize",
                //        "/oauth/token",
                //        "/social/signUp"
                //).permitAll()
                //// 任何请求都需要进行身份认证
                //.anyRequest()
                //// 授权的配置
                //.authenticated()
                //.and()
                // 防护伪造的功能
                .csrf().disable();
        // 有错误重新打开页面
        authorizeConfigManager.config(http.authorizeRequests());
    }

}