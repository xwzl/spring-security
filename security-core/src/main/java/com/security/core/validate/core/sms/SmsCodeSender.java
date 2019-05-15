package com.security.core.validate.core.sms;

/**
 * 发送短信
 *
 * @author xuweizhi
 * @date 2019/05/15 22:29
 */
public interface SmsCodeSender {

    /**
     * 向手机发送验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     */
    void send(String mobile, String code);
}
