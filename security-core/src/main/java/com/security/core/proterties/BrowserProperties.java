package com.security.core.proterties;

import com.security.core.constant.SecurityConstants;

/**
 * 浏览器端属性
 *
 * @author xuweizhi
 * @date 2019/05/15 1:02
 */
public class BrowserProperties {

    /**
     * 默认的 qq 登录后的注册页
     */
    private String signUrl = "/sign.html";

    /**
     * 设置默认的登录页面,在 BrowserController 进行替换逻辑
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    /**
     * 默认返回的类型，失败成功处理返回类型
     */
    private LoginType loginType = LoginType.JSON;

    /**
     * 配置 token  默认的失效时间
     */
    private int rememberMeSeconds = 3600;

    private SessionProperties session = new SessionProperties();

    public BrowserProperties() {
    }

    public BrowserProperties(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }
}
