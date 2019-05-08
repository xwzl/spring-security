package com.java.boot.base.until.redis;

import com.java.boot.system.property.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@EnableConfigurationProperties({RedisProperties.class})
@Component
public class RedisUtil {

    @Autowired
    private RedisProperties redisProperties;

    public void read() {
        System.out.println(redisProperties.getIp());
    }


    /**
     * 非切片链接池
     */
    private JedisPool jedisPool;

    /**
     * 非切片连接池初始化
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisProperties.getMaxTotal());
        config.setMaxIdle(redisProperties.getMaxIdle());
        config.setMaxWaitMillis(redisProperties.getMaxActive());
        config.setTestOnBorrow(redisProperties.getTestOnBorrow());
        config.setTestOnReturn(redisProperties.getTestOnReturn());
        jedisPool = new JedisPool(config, redisProperties.getIp(), redisProperties.getPort());
    }

    /**
     * 在多线程环境同步初始化
     */
    private synchronized void poolInit() {

        if (jedisPool == null) {
            initialPool();
        }

    }

    /**
     * 非切片客户端链接 同步获取非切片Jedis实例
     *
     */
    @SuppressWarnings("deprecation")
    public synchronized Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {

                jedis = jedisPool.getResource();
                // jedis.auth(password);
            }
        } catch (Exception e) {
            System.out.println("抛错");
            e.printStackTrace();
            // 释放jedis对象
            jedisPool.returnBrokenResource(jedis);
        } finally {
            // 返还连接池
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);

            }

        }
        return jedis;

    }


}





