package com.security.core.validate.core.impl;

import com.security.core.validate.core.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
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
        //String type = getProcessorType(request);
        //String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        //ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        //return (C) validateCodeGenerator.generate(request);
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码，用于比较验证时从中获取验证码的相关信息
     */
    private void save(ServletWebRequest request, C validateCode) {
        // ImageCode 无法序列化
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        sessionStrategy.setAttribute(request, getSessionKey(request), code);
    }

    /**
     * 获取 SESSION_KEY 的值
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }


    /**
     * 发送校验码，由子类实现
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


    /**
     * 根据请求的url获取校验码的类型
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        // getClass().getSimpleName() 最终实现类的简单名称，比如 ImageCodeProcessor 其结果是 Image
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        // 返回验证码枚举对象对应的字符串
        return ValidateCodeType.valueOf(type.toUpperCase());
    }


    @Override
    public void validate(ServletWebRequest request) {

        // 验证验证的类型是短信验证还是验证码验证
        ValidateCodeType codeType = getValidateCodeType(request);
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request));

        // 获取前端传递过来的验证码，与 ValidateCodeType 直接作用
        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        // 验证码不能为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "请填写验证码");
        }

        // 验证码不存在
        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        // 验证码已过期
        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, getSessionKey(request));
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        // 验证码不匹配
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, getSessionKey(request));
    }
}
