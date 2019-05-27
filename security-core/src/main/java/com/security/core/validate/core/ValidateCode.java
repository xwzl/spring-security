package com.security.core.validate.core;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信验证码
 *
 * @author xuweizhi
 * @date 2019/05/15 18:36
 */
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 8418486433439613686L;

    /**
     * 状态码
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;


    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }


    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
