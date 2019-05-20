package com.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 社交认证过滤器后处理器
 *
 * @author xuweizhi
 * @date 2019/05/20 16:07
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
