package com.security.core.social;

import com.security.core.proterties.SecurityProperties;
import com.security.core.social.support.CoreSpringSocialConfigurer;
import com.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationProvider;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 数据库连接配置, 把SpringSocial社交相应的特性启动起来
 *
 * @author xuweizhi
 * @date 2019/05/15 15:29
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * 不一定每一个项目都要注册，这是一个授权后默认登录的注入，不需要注册,/user/regist 就会无效
     */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /**
     * {@link SocialAuthenticationProvider#toUserId},查询
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // 未做任何的加密解密操作，JdbcUsersConnectionRepository 点进去，有一个建表的脚本
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        // 只能更改数据中的前缀，不能改变表明
        //repository.setTablePrefix("my_");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 注册 social 拦截链,定义认证的授权地址
     */
    @Bean
    public SpringSocialConfigurer coreSecuritySocialConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        CoreSpringSocialConfigurer configurer = new CoreSpringSocialConfigurer(filterProcessesUrl);
        // 设置social中的注册页为
        configurer.signupUrl(securityProperties.getBrowser().getSignUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
        //return new UserIdSource() {
        //    @Override
        //    public String getUserId() {
        //        return "anonymous";
        //    }
        //};
    }

}
