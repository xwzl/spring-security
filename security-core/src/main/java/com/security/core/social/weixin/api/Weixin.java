package com.security.core.social.weixin.api;

/**
 * 微信接口
 * @author xuweizhi
 */
public interface Weixin {

    /**
     * openId
     */
    WeixinUserInfo getUserInfo(String openId);
}
