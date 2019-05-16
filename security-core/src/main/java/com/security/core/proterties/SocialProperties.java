package com.security.core.proterties;

import com.security.core.social.SocialConfig;

/**
 * 用来处理第三方登录的配置
 *
 * @author xuweizhi
 * @date 2019/05/15 16:16
 */
public class SocialProperties {

    private QQProperties qq = new QQProperties();

    /**
     * {@link SocialConfig},配置人认证的地址
     */
    private String filterProcessesUrl = "/auth";

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
