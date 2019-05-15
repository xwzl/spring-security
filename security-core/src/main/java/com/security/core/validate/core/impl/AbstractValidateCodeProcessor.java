package com.security.core.validate.core.impl;

import com.security.core.validate.core.ValidateCode;
import com.security.core.validate.core.ValidateCodeGenerator;
import com.security.core.validate.core.ValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * Processor 抽象的实现
 *
 * @author xuweizhi
 * @date 2019/05/15 23:19
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    /**
     * 创建验证码
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getProcessorType(request);
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        return (C) validateCodeGenerator.generate(request);
    }


    /**
     * 保存校验码
     */
    private void save(ServletWebRequest request, C validateCode) {
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request), validateCode);

    }


    /**
     * 发送校验码，由子类实现
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


    /**
     * 根据请求的 url 获取校验码的类型
     */
    public String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

}
