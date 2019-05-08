package com.java.boot.base.until.redis;

import java.util.List;
import java.util.Map;

/**
 * @author xuweizhi
 */
public interface RedisService {

    void setString(String key, int seconds, String value);

    boolean exist(String key);

    /**
     * 存储String
     */
    <T> boolean setString(String key, String param);

    /**
     * 获取Stringֵ
     */
    String getString(String key);

    /**
     * 存储实体类
     */
    <T> boolean setBean(String key, Object bean);

    /**
     * 获取实体类
     */
    <T> T getBean(String key);

    /**
     * 存储 list
     */
    <T> boolean setList(String key, List<T> list);

    /**
     * 获取list
     */
    <T> List<T> getList(String key);

    /**
     * 存储 map
     */
    <T> boolean setMap(String key, Map<String, T> map);

    /**
     * 获取map
     */
    <T> Map<String, T> getMap(String key);

    boolean del(String key);

    <T> boolean setInteger(String key, Integer num);

    Integer getSetInteger(String key, Integer num);

    Integer getInteger(String key);

    <T> boolean setHash(String key, Map<String, String> map);

    Map<String, String> getAllHash(String key);

    List<String> getHashm(String key, String... fields);

    String login(String userId, int second);

    Boolean validate(String token);

    void logout(String token);

    public String getUserId(String token);

}