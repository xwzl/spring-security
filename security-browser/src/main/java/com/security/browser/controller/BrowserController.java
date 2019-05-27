package com.security.browser.controller;

import com.security.core.proterties.SecurityProperties;
import com.security.core.social.support.SocialUserInfo;
import com.security.core.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理不同的登录请，接收到 html 请求或数据请求，根据是否需要身份认证，跳转到自定义的controller方法上，
 * 在方法内判断是否是 html 请求引发的跳转，是返回登录页面，不是返回错误信息
 *
 * @author xuweizhi
 * @date 2019/05/15 0:38
 */
@RestController
public class BrowserController {

    private final Logger log = LoggerFactory.getLogger(BrowserController.class);

    /**
     * 跳转之前，把请求缓存到session 中
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 跳转的工具类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 注入自定义的安全配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时，跳转到这里,如果不是.html 结尾的请求，就返回授权的状态码。
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // 如果当前请求不为空
        if (savedRequest != null) {
            // 获取当前请求的 url 地址
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是：" + redirectUrl);
            // 引发跳转的请求是不是以 .html 结尾的
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                // 请求转发到登录页面，且可自定义登录页面,配置跳转页面
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页!");
    }

    /**
     * todo 获取社交用户信息
     */
    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        // 暂时不可用
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadImg(connection.getImageUrl());
        //userInfo.setProviderId("id");
        //userInfo.setProviderUserId("key");
        //userInfo.setNickname("nickName");
        //userInfo.setHeadImg("headImg");

        return userInfo;
    }

    /**
     * 失效地址
     */
    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse sessionInvalid() {
        String message = "session失效";
        return new SimpleResponse(message);
    }
}
