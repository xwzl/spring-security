package com.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuweizhi
 * @date 2019/05/16 1:35
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SECURITY_CORE_MOBILE_KEY = "mobile";

    /**
     * 手机登录的参数名称
     */
    private String mobileParameter = SECURITY_CORE_MOBILE_KEY;

    /**
     * 当前过滤器是否只处理 post 请求
     */
    private boolean postOnly = true;

    /**
     * 验证短信登录的请求，请求地址是/authentication/mobile
     */
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }


    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter(){
        return mobileParameter;
    }

    public void setMobileParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.mobileParameter = usernameParameter;
    }


}
