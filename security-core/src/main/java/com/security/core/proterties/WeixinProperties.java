package com.security.core.proterties;

import com.security.core.proterties.source.SocialProperties;

/**
 * 微信登录配置项
 *
 * @author xuweizhi
 * @date 2019/05/20 16:31
 */
public class WeixinProperties extends SocialProperties {

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";

    /**
     * @return the providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }



}
