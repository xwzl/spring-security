package com.security.core.social.qq.config;

import com.security.core.proterties.QQProperties;
import com.security.core.proterties.SecurityProperties;
import com.security.core.social.qq.config.source.SocialAutoConfigurerAdapter;
import com.security.core.social.qq.connet.QQConnectionFactory;
import com.security.core.social.view.CoreConnectView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * QQ 连接工厂, ConditionalOnProperty 的作用是配置 app-id 才有效.才注册 bean
 * `
 * com.security.social.qq.app-id被配置才有效
 *
 * @author xuweizhi
 * @date 2019/05/15 16:20
 */
@Configuration
@ConditionalOnProperty(prefix = "com.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qq = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
    }

    /**
     * 返回绑定或者解绑信息
     */
    @Bean("connect/qqConnected")
    @ConditionalOnMissingBean
    public View qqConnectedView() {
        return new CoreConnectView();
    }

}
