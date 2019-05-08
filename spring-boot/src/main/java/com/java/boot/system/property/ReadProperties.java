package com.java.boot.system.property;


import org.jetbrains.annotations.Contract;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 获取Redis
 *
 * @author xuweizhi
 */
@EnableConfigurationProperties({RedisProperties.class})
@Component
public class ReadProperties {


    private RedisProperties redisProperties;

    @Contract(pure = true)
    public ReadProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    public void read() {
        System.out.println(redisProperties);
    }

}
