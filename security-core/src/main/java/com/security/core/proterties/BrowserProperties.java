package com.security.core.proterties;

/**
 * @author xuweizhi
 * @date 2019/05/15 1:02
 */
public class BrowserProperties {

    /**
     * 设置默认的登录页面,在 BrowserController 进行替换逻辑
     */
    private String loginPage = "/login.html";

    /**
     * 默认返回的类型，失败成功处理返回类型
     */
    private LoginType loginType = LoginType.JSON;

    /**
     * 配置 token  默认的失效时间
     */
    private int rememberMeSeconds = 3600;

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
}
