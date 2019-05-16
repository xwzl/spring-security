package com.security.core.proterties;

/**
 * 验证码配置
 *
 * @author xuweizhi
 * @date 2019/05/15 19:58
 */
public class ValidateCodeProperties {

    private ImageCodeProperties imageCode = new ImageCodeProperties();

    private SmsCodeProperties smsCode = new SmsCodeProperties();

    public ImageCodeProperties getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImageCodeProperties imageCode) {
        this.imageCode = imageCode;
    }

    public SmsCodeProperties getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(SmsCodeProperties smsCode) {
        this.smsCode = smsCode;
    }

}
