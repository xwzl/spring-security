package com.security.core.validate.core;

import org.springframework.security.core.AuthenticationException;

/**
 * @author xuweizhi
 * @date 2019/05/15 19:15
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }

}
