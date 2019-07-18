package com.security.core.social.qq.connet;

import com.security.core.social.qq.api.QQ;
import com.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 服务提供商的代码
 *
 * @author xuweizhi
 * @date 2019/05/15 14:57
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    /**
     * Step1：获取Authorization Code,引导用于跳转需要的地址
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * Step2：通过Authorization Code获取Access Token
     * <p>
     * http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    private String appId;

    /**
     * 应用对应的 appId 和 appSecret
     *
     * @param appId     应用对应的id
     * @param appSecret 应用对应的 appSecret
     */
    public QQServiceProvider(String appId, String appSecret) {
        // qq 互联的 appId 和 appSecret
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }

}
