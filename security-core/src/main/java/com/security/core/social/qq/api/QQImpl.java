package com.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * 第六步获取用户信息 api 获取用户信息
 * <p>
 * 多实例对象，每个
 *
 * @author xuweizhi
 * @date 2019/05/15 14:17
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 获取 openid
     * <p>
     * http://wiki.connect.qq.com/openapi%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E_oauth2-0
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 完整从 url ,但是 access_token 交给父类处理
     * https://graph.qq.com/user/get_user_info?access_token=%S&oauth_consumer_key=%S&openid=%S
     */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%S&openid=%S";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        // 父类默认的策略是把 token 放在请求头，qq 获取用户信息，需要作为参数使用 TokenStrategy.ACCESS_TOKEN_PARAMETER
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        // 获取 openId
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        // callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        System.out.println(result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
    }

    @Override
    public QQUserInfo getUserInfo() {

        String url = String.format(URL_GET_USER_INFO, appId, openId);

        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);
        QQUserInfo userInfo = null;
        // 转成 json 对象
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            //userInfo.setOpenId(this.openId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
