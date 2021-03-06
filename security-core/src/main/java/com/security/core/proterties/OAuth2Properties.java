package com.security.core.proterties;

/**
 * @author xuweizhi
 * @since 2019/06/09 22:08
 */
public class OAuth2Properties {

    /**
     * 使用 jwt 时为 token 签名的秘钥,发放用来签发令牌
     */
    private String jwtSigningKey = "imooc";
    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

}
