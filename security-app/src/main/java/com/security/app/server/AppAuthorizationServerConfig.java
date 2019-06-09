package com.security.app.server;

import com.security.core.proterties.OAuth2ClientProperties;
import com.security.core.proterties.SecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.security.Principal;
import java.util.Map;

/**
 * 认证资源服务器,依赖该项目的模块已经是资源服务器了
 * <p>
 * http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=imooc&redirect_url=http://127.0.0.1:8080&scope=all
 * <p>
 * client_id: 应用 id
 * redirect_url: 重定向地址
 * <p>
 * {@link TokenEndpoint#postAccessToken(Principal, Map)} OAuth2 验证的起点
 *
 * @author xuweizhi
 * @date 2019/05/28 23:55
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 继承了AuthorizationServerConfigurerAdapter类，就需要注入userDetailsService和authenticationManager
     * 如果没有继承，使用默认的机制不需要注入，Spring自己回去查找
     */
    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 替换默认的 Token 处理方式
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 配置自定义存储方式
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService);
    }

    /**
     * 继承 AuthorizationServerConfigurerAdapter 这个类之后，Spring 默认提供的 client 和 secret 配置将无效
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //clients.inMemory().
        //        withClient("imooc")
        //        .secret(passwordEncoder.encode("imoocsecret"))
        //        // 令牌有效时间 7200 s
        //        .accessTokenValiditySeconds(7200)
        //        // 令牌支持的模式，一共有四种，这是用密码登录的模式
        //        .authorizedGrantTypes("refresh_token", "password")
        //        // 权限
        //        .scopes("all", "read", "write")
        //// 多客户端
        //       /* .and()
        //        .withClient()*/;

        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (OAuth2ClientProperties config : securityProperties.getOauth2().getClients()) {
                builder.withClient(config.getClientId())
                        .secret(passwordEncoder.encode(config.getClientSecret()))
                        // 令牌有效时间 7200 s
                        .accessTokenValiditySeconds(config.getAccessTokenValidateSeconds())
                        // 令牌支持的模式，一共有四种，这是用密码登录的模式
                        .authorizedGrantTypes("refresh_token", "password")
                        // 权限
                        .scopes("all", "read", "write");
            }
        }
    }

}
