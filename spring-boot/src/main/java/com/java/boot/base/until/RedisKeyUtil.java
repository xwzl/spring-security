package com.java.boot.base.until;

/**
 * @author xuweizhi
 * @date 2018/11/14 10:42
 */
public class RedisKeyUtil {

    /**
     * redis的key
     * 形式为：
     * 表名:主键名:主键值:列名
     *
     * @param tableName     表名
     * @param majorKey      主键名
     * @param majorKeyValue 主键值
     * @param column        列名
     */
    public static String getKeyWithColumn(String tableName, String majorKey, String majorKeyValue, String column) {
        return tableName + ":" +
                majorKey + ":" +
                majorKeyValue + ":" +
                column;
    }

    /**
     * redis的key
     * 形式为：
     * 表名:主键名:主键值
     *
     * @param tableName     表名
     * @param majorKey      主键名
     * @param majorKeyValue 主键值
     */
    public static String getKey(String tableName, String majorKey, String majorKeyValue) {
        return tableName + ":" +
                majorKey + ":" +
                majorKeyValue + ":";
    }

}
