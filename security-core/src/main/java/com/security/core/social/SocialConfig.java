package com.security.core.social;

/**
 * 数据库连接配置
 *
 * @author xuweizhi
 * @date 2019/05/15 15:29
 */
//@Configuration
//@EnableSocial
public class SocialConfig /*extends SocialConfigurerAdapter*/ {

    //@Autowired
    //private DataSource dataSource;
    //
    //@Override
    //public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
    //    // 未做任何的加密解密操作，JdbcUsersConnectionRepository 点进去，有一个建表的脚本
    //    JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    //    // 只能更改数据中的前缀，不能改变表明
    //    repository.setTablePrefix("my_");
    //    return repository;
    //}
    //
    ///**
    // * 注册 social 拦截链
    // */
    //@Bean
    //public SpringSocialConfigurer mySecuritySocailConfig(){
    //     return new SpringSocialConfigurer();
    //}

}
