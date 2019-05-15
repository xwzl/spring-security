package com.security.browser.config;

import com.security.core.proterties.SecurityProperties;
import com.security.core.validate.core.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

import static com.security.core.constant.URLConstant.*;

/**
 * 权限认证
 *
 * @author xuweizhi
 * @date 2019/05/13 21:31
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private BrowserAuthenticationSuccessHandler browserSuccessHandler;

    @Autowired
    private BrowserAuthenticationFailureHandler browserFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetailService;

    //@Autowired
    //private SpringSocialConfigurer mySecuritySocialConfig;


    /**
     * 注入加密解密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 其默认实现
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置token 缓存
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 启动的时候创建表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    //@Override
    //public void configure(WebSecurity web) throws Exception {
    //    web.ignoring().antMatchers("/index", "/static/**", "/favicon.ico");
    //}


    /**
     * http 的验证开始
     *
     * @param http 过滤 http 请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        // 初始化配置
        validateCodeFilter.setAuthenticationFailureHandler(browserFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();


        // 设置验证码校验在 密码处理之前
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                /* .apply(mySecuritySocialConfig)*/
                // 默认提供的 form 表单登录
                //http.httpBasic()
                // form 表单登录进行身份认证，所有的请求都需要身份认证才能访问
                .formLogin()
                    // 登录需要认证的页面，但是由于自身也需要进行身份认证，所以一直循环认证自身
                    // 配置 thymeleaf,需要配置controller 进行路径转发
                    .loginPage(AUTHENTICATION_URL)
                    // 请求登陆处理地址,登录的 url 不用特意写controller
                    .loginProcessingUrl(LOGIN_URL)
                    // 表单登录成功后的 handler 处理器
                    .successHandler(browserSuccessHandler)
                    // 失败处理
                    .failureHandler(browserFailureHandler)
                    .and()
                .rememberMe()
                    // 添加 token
                    .tokenRepository(persistentTokenRepository())
                    // 配置过期时间
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    // 这个去做登录
                    .userDetailsService(myUserDetailService)
                .and()
                // 判断之前的过滤配置，进行授权
                .authorizeRequests()
                // 以下路径不需要进行身份认证,securityProperties.getBrowser().getLoginPage()是真正的登录页面，包括用户自定义的
                .antMatchers(AUTHENTICATION_URL, securityProperties.getBrowser().getLoginPage(), "/code/image", STATIC_RESOURCES_URL, "favicon.ico").permitAll()
                // 任何请求都需要进行身份认证
                .anyRequest()
                // 授权的配置
                .authenticated()
                .and()
                // 防护伪造的功能
                .csrf().disable();
    }

}
