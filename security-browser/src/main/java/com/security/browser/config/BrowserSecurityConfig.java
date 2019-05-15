package com.security.browser.config;

import com.security.core.proterties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        // 默认提供的 form 表单登录
        //http.httpBasic()
        // form 表单登录进行身份认证，所有的请求都需要身份认证才能访问
        http/*.apply(mySecuritySocialConfig)
                .and()*/
                .formLogin()
                // 登录需要认证的页面，但是由于自身也需要进行身份认证，所以一直循环认证自身
                // 配置 thymeleaf,需要配置controller 进行路径转发
                .loginPage("/authentication/require")
                // 请求登陆处理地址
                .loginProcessingUrl("/login/form")
                // 表单登录成功后的 handler 处理器
                .successHandler(browserSuccessHandler)
                // 失败处理
                .failureHandler(browserFailureHandler)
                .and()
                // 判断之前的过滤配置，进行授权
                .authorizeRequests()
                // 以下路径不需要进行身份认证,securityProperties.getBrowser().getLoginPage()是真正的登录页面，包括用户自定义的
                .antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(), "**.js", "**.css", "favicon.ico").permitAll()
                // 任何请求都需要进行身份认证
                .anyRequest()
                // 授权的配置
                .authenticated()
                .and()
                // 防护伪造的功能
                .csrf().disable();
    }

}
