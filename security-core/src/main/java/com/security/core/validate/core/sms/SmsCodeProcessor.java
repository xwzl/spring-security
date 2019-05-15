package com.security.core.validate.core.sms;

import com.security.core.constant.SecurityConstants;
import com.security.core.validate.core.ValidateCode;
import com.security.core.validate.core.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author xuweizhi
 * @date 2019/05/16 0:43
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        // 这是获取那个啥呢？获取mobile phone number
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }

}
