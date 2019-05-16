package com.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 实现 QQ 应用拦截路径
 *
 * @see SocialConfig
 * @author xuweizhi
 * @date 2019/05/16 14:47
 */
public class CoreSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public CoreSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 改变转发链上的处理逻辑
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
