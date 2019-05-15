package com.security.core.validate.core.sms;

import com.security.core.proterties.SecurityProperties;
import com.security.core.validate.core.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 原来这样进行逻辑替换的
 *
 * @author xuweizhi
 * @date 2019/05/15 20:42
 */
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public ValidateCode generator(ServletWebRequest request) {
         // 生成验证按的长度
         String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
         return new ValidateCode(code, securityProperties.getCode().getImage().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
