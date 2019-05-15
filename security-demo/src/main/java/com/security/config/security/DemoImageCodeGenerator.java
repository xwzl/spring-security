package com.security.config.security;

import com.security.core.validate.core.ValidateCode;
import com.security.core.validate.core.ValidateCodeBeanConfig;
import com.security.core.validate.core.ValidateCodeController;
import com.security.core.validate.core.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 覆盖掉默认的 image 配置bean,这个 Bean ，会在 {@link ValidateCodeController} 被使用,
 * 在{@link ValidateCodeBeanConfig} 是其默认配置
 * @author xuweizhi
 * @date 2019/05/15 20:55
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        System.out.println("更高级图形验证am");
        return null;
    }
}

