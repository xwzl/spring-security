package com.security.core.validate.core;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 *
 * @author xuweizhi
 * @date 2019/05/15 23:13
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入 session 时的前缀
     */
    public final static String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * 创建校验码器
     *
     * @param request request
     * @throws Exception
     */

    void process(ServletWebRequest request) throws Exception;

}
