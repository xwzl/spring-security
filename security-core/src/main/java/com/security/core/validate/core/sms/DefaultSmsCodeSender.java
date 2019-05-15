package com.security.core.validate.core.sms;

/**
 * 发送验证码的默认实现
 *
 * @author xuweizhi
 * @date 2019/05/15 22:32
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机" + mobile + "发送短信验证码" + code);
    }

}
