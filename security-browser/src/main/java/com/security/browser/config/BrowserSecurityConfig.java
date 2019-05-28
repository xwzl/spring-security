package com.security.browser.config;

import com.security.core.authentication.browser.FormAuthenticationConfig;
import com.security.core.authentication.mobile.SmsCodeAuthenticationConfig;
import com.security.core.proterties.SecurityProperties;
import com.security.core.validate.core.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

import static com.security.core.constant.SecurityConstants.*;

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
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

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

        // 默认的表单拦截
        formAuthenticationConfig.configure(http);

        // 验证码认证安全配置
        http.apply(validateCodeSecurityConfig)
                .and()
                // 应用短信验证码认证安全配置
                .apply(smsCodeAuthenticationConfig)
                    .and()
                .apply(coreSecuritySocialConfig)
                    .and()
                .rememberMe()
                    // 添加 token
                    .tokenRepository(persistentTokenRepository())
                    // 配置过期时间
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    // 这个去做登录
                    .userDetailsService(myUserDetailsService)
                    .and()
                    // session 管理
                .sessionManagement()
                    // invalid 失效后的地址
                    //.invalidSessionUrl("/session/invalid")
                    // 最大登录 session 数量,默认踢出第一个用户
                    .invalidSessionStrategy(invalidSessionStrategy)
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    // 当 session 数量到达最大后阻止 session 行为
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                    // session 被挤下去后处理策略
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    // 两个 and
                    .and()
                    .and()
                .logout()
                    // 突出请求 url ,默认是 logout
                    .logoutUrl("/signOut")
                    // 突出成功默认处理 url 为登陆的 url
                    //.logoutSuccessUrl("demo-logout.html")
                    // 退出成功处理器，和 url 二选一，配置退出成功处理逻辑
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                    .and()
                // 判断之前的过滤配置，进行授权
                .authorizeRequests()
                // 以下路径不需要进行身份认证,securityProperties.getBrowser().getLoginPage()是真正的登录页面，包括用户自定义的
                .antMatchers(
                        DEFAULT_UNAUTHENTICATION_URL,
                        securityProperties.getBrowser().getLoginPage(),
                        // 配置 qq 授权后的注册页面
                        securityProperties.getBrowser().getSignUrl(),
                        securityProperties.getBrowser().getSignOutUrl()== null?"404":securityProperties.getBrowser().getSignOutUrl(),
                        DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        STATIC_RESOURCES_URL,
                        "favicon.ico",
                        "/user/regist",
                        DEFAULT_SESSION_INVALID_URL
                ).permitAll()
                // 任何请求都需要进行身份认证
                .anyRequest()
                // 授权的配置
                .authenticated()
                .and()
                // 防护伪造的功能
                .csrf().disable();
    }

}
