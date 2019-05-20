package com.security.core.social.weixin.config;


import com.security.core.proterties.SecurityProperties;
import com.security.core.proterties.WeixinProperties;
import com.security.core.social.qq.config.source.SocialAutoConfigurerAdapter;
import com.security.core.social.view.CoreConnectView;
import com.security.core.social.weixin.connet.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;


@Configuration
@ConditionalOnProperty(prefix = "com.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }

    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new CoreConnectView();
    }
}
