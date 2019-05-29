package com.security.app.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

import java.security.Principal;
import java.util.Map;

/**
 * 认证资源服务器,依赖该项目的模块已经是资源服务器了
 *
 * http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=49596fb9-19a1-462c-b3d8-59d173438aa0&redirect_url=http://127.0.0.1:8080&scope=all
 *
 * client_id: 应用 id
 * redirect_url: 重定向地址
 *
 * {@link TokenEndpoint#postAccessToken(Principal, Map)} OAuth2 验证的起点
 *
 * @author xuweizhi
 * @date 2019/05/28 23:55
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthorizationServerConfig {

}
