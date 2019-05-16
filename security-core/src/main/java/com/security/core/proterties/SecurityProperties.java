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

    //private SocialProperties socialProperties = new SocialProperties();

    /**
     * 验证码属性配置
     */
    private ValidateCodeProperties validateCode = new ValidateCodeProperties();


    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(ValidateCodeProperties validateCode) {
        this.validateCode = validateCode;
    }

    //public SocialProperties getSocialProperties() {
    //    return socialProperties;
    //}
    //
    //public void setSocialProperties(SocialProperties socialProperties) {
    //    this.socialProperties = socialProperties;
    //}

}
