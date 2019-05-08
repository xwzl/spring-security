package com.java.boot.system.config.other;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

import java.io.Serializable;
import java.time.Duration;

/**
 * Redis缓存配置
 *
 * @author xuweizhi
 * @date 2019/04/23 15:24
 */
@Configuration
@EnableCaching
public class RedisCacheConfigure extends CachingConfigurerSupport {


    /**
     * Spring Cache提供的@Cacheable注解不支持配置过期时间，还有缓存的自动刷新。
     * 我们可以通过配置CacheManneg来配置默认的过期时间和针对每个缓存容器（value）单独配置过期时间，但是总是感觉不太灵活。
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //Redis加锁的写入器
        //RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
        //启动Redis默认设置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //.entryTtl(this.timeToLive)
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    @NotNull
    @Contract(value = " -> new", pure = true)
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    @NotNull
    @Contract(" -> new")
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @NotNull
    private ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 解决jackson2无法反序列化LocalDateTime的问题，新增的如果出错，删除
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        return om;
    }

    /**
     * 设置RedisTemplate序列化器
     *
     * @param rcf 注入Redis工厂
     * @return 返回Redis模板
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory rcf) {

        RedisTemplate<String, Object> rt = new RedisTemplate<>();
        // 配置连接工厂
        rt.setConnectionFactory(rcf);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = getObjectMapper();
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置value的序列化规则和 key的序列化规则
        rt.setValueSerializer(jackson2JsonRedisSerializer);
        rt.setKeySerializer(new StringRedisSerializer());
        rt.setHashKeySerializer(jackson2JsonRedisSerializer);
        rt.setHashValueSerializer(jackson2JsonRedisSerializer);
        rt.setDefaultSerializer(jackson2JsonRedisSerializer);
        rt.setEnableDefaultSerializer(true);
        rt.afterPropertiesSet();
        return rt;
    }

    /**
     * 定义 UserRedisTemplate ，指定序列化和反序列化的处理类
     *
     * @param factory redis连接工厂
     * @return 模板
     */
    @Bean("UserRedisTemplate")
    public RedisTemplate<Serializable, Object> userRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Serializable, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> j2jrs = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = getObjectMapper();
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        j2jrs.setObjectMapper(om);
        // 序列化 value 时使用此序列化方法
        template.setValueSerializer(j2jrs);
        template.setHashValueSerializer(j2jrs);
        // 序列化 key 时
        StringRedisSerializer srs = new StringRedisSerializer();
        template.setKeySerializer(srs);
        template.setHashKeySerializer(srs);
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "redisGenericTemplate")
    @SuppressWarnings({"unchecked","rawtypes"})
    public <T> RedisTemplate<String, T> redisGenericTemplate(RedisConnectionFactory rcf) {

        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(rcf);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**配置其他类型的redisTemplate***/
    @Bean
    @SuppressWarnings({"unchecked","rawtypes"})
    public RedisTemplate<Object, Object> redisTemplateKeyObject(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 根据方法名注入对象
     */
    @Bean
    @SuppressWarnings("rawtypes")
    public ValueOperations opsForValue(@NotNull RedisTemplate redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    @SuppressWarnings({"rawtypes"})
    public ListOperations opsForList(@NotNull RedisTemplate redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    @SuppressWarnings({"rawtypes"})
    public HashOperations opsForHash(@NotNull RedisTemplate redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    @SuppressWarnings({"rawtypes"})
    public SetOperations opsForSet(@NotNull RedisTemplate redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    @SuppressWarnings({"rawtypes"})
    public ZSetOperations opsForZSet(@NotNull RedisTemplate redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    /**
     * 自定义redis key值生成策略
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

      /*
    private Duration timeToLive = Duration.ZERO;

    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }
    RedisCacheManager的基本属性
    @SuppressWarnings("rawtypes") //
    配置redisTemplate 通过构造函数
    private final RedisOperations redisOperations;
    //是否使用前缀
    private boolean usePrefix = false;
    //默认前缀 为":"。使用前缀可以对缓存进行分组，避免缓存的冲突
    private RedisCachePrefix cachePrefix = new DefaultRedisCachePrefix();
    //远程加载缓存
    private boolean loadRemoteCachesOnStartup = false;
    //是否动态生成缓存。默认为true。这个就是上面如果缓存不存在，则创建
    //是通过这个属性进行配置。配置为false则不会去创建缓存
    private boolean dynamic = true;

    // 过期时间 0为永不过期
    private long defaultExpiration = 0;
    //可以配置指定key的过期时间 可以通过定制化配置过期时间
    private Map<String, Long> expires = null;
    //配置缓存名称集合
    private Set<String> configuredCacheNames;
    //缓存是否可以为null
    private final boolean cacheNullValues;
    */


    /**
     * 自定义CacheManager
     */
    //@Bean
    //public CacheManager cacheManager(RedisTemplate redisTemplate) {
    //    //全局redis缓存过期时间
    //    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMillis(10));
    //    RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    //    return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    //}

    //@Bean
    //public CacheManager cacheManager(RedisTemplate redisTemplate) {
    //    RedisCacheManager cacheManager= new RedisCacheManager(redisTemplate);
    //    cacheManager.setDefaultExpiration(60);
    //    Map<String,Long> expiresMap=new HashMap<>();
    //    expiresMap.put("Product",5L);
    //    cacheManager.setExpires(expiresMap);
    //    return cacheManager;
    //}


}