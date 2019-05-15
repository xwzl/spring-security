package com.security.core.proterties;

/**
 * 短信验证码的属性
 *
 * @author xuweizhi
 * @date 2019/05/15 19:56
 */
public class SmsCodeProperties {

    /**
     * 短信验证码的长度
     */
    private int length=4;

    /**
     * 过期时间
     */
    private int expireIn = 60;

    /**
     * 短信验证路径
     */
    private String url;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
