package com.security.core.validate.core;

import com.security.core.proterties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.security.core.constant.SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE;


/**
 * 短信验证拦截
 *
 * @author xuweizhi
 * @date 2019/05/15 19:02
 */
public class SmsValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加出现异常的接口
     */
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 获取请求中的缓存值
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 自定配置基类
     */
    private SecurityProperties securityProperties;

    /**
     * 验证按 urls 拦截配置
     */
    private Set<String> urls = new HashSet<>();

    /**
     * url 路径处理的工具类，由 Spring 提供
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        if (StringUtils.isNoneBlank(securityProperties.getValidateCode().getImageCode().getUrl())) {
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getValidateCode().getSmsCode().getUrl(), ",");
            urls.addAll(Arrays.asList(configUrls));
        }

        // 登录请求的图片验证一定要加
        urls.add(DEFAULT_LOGIN_PROCESSING_URL_MOBILE);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;

        for (String url : urls) {
            // 传进来的请求能够与缓存中的请求匹配上,就进行验证码的校验
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        // 如果是登录请求进行验证码校验
        if (action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException ex) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, ex);
                return;
            }
        }
        // 如果不是验证码校验，直接交给下一个过滤器
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, "SESSION_KEY_FOR_CODE_sms");

        // 与前端页面的输入框有关系，imageCode
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");

        // 验证码不能为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("请填写验证码");
        }

        // 验证码不存在
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        // 验证码已过期
        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        // 验证码不匹配
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
