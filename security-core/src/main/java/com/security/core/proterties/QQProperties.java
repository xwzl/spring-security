package com.security.core.proterties;

import com.security.core.proterties.source.SocialProperties;

/**
 * @author xuweizhi
 * @date 2019/05/15 16:07
 */
public class QQProperties extends SocialProperties {

    /**
     * 服务提供商的标志
     */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
