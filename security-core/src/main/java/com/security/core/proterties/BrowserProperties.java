package com.security.core.proterties;

/**
 * @author xuweizhi
 * @date 2019/05/15 1:02
 */
public class BrowserProperties {


    /**
     * 设置默认的登录页面
     */
    private String loginPage = "/login.html";

    private LoginType loginType = LoginType.JSON;

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
}
