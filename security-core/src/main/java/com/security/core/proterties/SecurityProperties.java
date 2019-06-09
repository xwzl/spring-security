package com.security.core.proterties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用于封装权限信息
 * 用于读取所有以 com.security 开头的配置项
 *
 * @author xuweizhi
 * @date 2019/05/15 1:02
 */
@ConfigurationProperties(prefix = "com.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    /**
     * social 相关配置
     */
    private SocialProperties social = new SocialProperties();

    /**
     * 验证码属性配置
     */
    private ValidateCodeProperties validateCode = new ValidateCodeProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public ValidateCodeProperties getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(ValidateCodeProperties validateCode) {
        this.validateCode = validateCode;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
