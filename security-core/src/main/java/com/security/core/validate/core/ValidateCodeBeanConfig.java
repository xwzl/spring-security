package com.security.core.validate.core;

import com.security.core.proterties.SecurityProperties;
import com.security.core.validate.core.image.ImageCodeGenerator;
import com.security.core.validate.core.sms.DefaultSmsCodeSender;
import com.security.core.validate.core.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证类型的 bean 配置
 *
 * @author xuweizhi
 * @date 2019/05/15 20:48
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 如果 Spring 容器存在 imageCodeGenerator Bean ，就不会使用这个 Bean
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * 如果 Spring 容器存在 smsCodeSender Bean ，就不会使用这个 Bean
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
