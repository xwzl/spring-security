package com.security.core.validate.core;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常，继承身份验证异常的基类
 *
 * @author xuweizhi
 * @date 2019/05/15 19:15
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    /**
     * 实现一个父类的构造方法
     */
    public ValidateCodeException(String msg) {
        super(msg);
    }

}
