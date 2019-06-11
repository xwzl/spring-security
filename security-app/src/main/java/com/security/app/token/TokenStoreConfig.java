package com.security.app.token;

import com.security.app.jwt.AppJwtTokenEnhancer;
import com.security.core.proterties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author xuweizhi
 * @since 2019/06/09 22:53
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 同下
     */
    @Bean
    @ConditionalOnProperty(prefix = "com.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 声明 Jwt 令牌替换默认的令牌
     * <p>
     * 当配置项前缀 com.security.oauth2 ，属性  storeType；
     * 当 com.security.oauth2.storeType=jwt是配置才会生效，matchIfMissing 配置文件中不写这个属性，这个配置会失效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "com.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JWtTokenConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * Jwt 只管只存储，不管生成，这个是专门用来进行 token 处理的。
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            // 签名用的秘钥,Spring 默认是 UTF-8 ,因此验证签名时必须指定字符串的格式
            accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return accessTokenConverter;
        }

        /**
         * 提供一个默认的 jwt 扩展 bean,当有配置相同 bean 时，这个将会被覆盖
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer() {
            return new AppJwtTokenEnhancer();
        }
    }
}
