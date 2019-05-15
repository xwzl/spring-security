package com.security.core.social.qq.config;

/**
 * QQ 连接工厂, ConditionalOnProperty 的作用是配置 appid 才有效.才注册 bean
 *
 *
 * @author xuweizhi
 * @date 2019/05/15 16:20
 */
//@Configuration
//@ConditionalOnProperty(prefix = "com.security.social.qq",name = "app-id")
public class QQAutoConfig /*extends SocialAutoConfigurerAdapter*/ {

    //@Autowired
    //private SecurityProperties securityProperties;
    //
    //@Override
    //protected ConnectionFactory<?> createConnectionFactory() {
    //    QQProperties qq = securityProperties.getSocialProperties().getQq();
    //    return new QQConnectionFactory(qq.getProviderId(),qq.getAppId(),qq.getAppSecret());
    //}

}
