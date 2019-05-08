package com.java.boot.system.config.data;

import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置数据源上下文
 * <p>
 * 我们需要新建一个数据源上下文，用户记录当前线程使用的数据源的key是什么，以及记录所有注册成功的数据源的key的集合。
 * 对于线程级别的私有变量，我们首先ThreadLocal来实现。
 *
 * @author xuweizhi
 * @since 2019/04/25 11:07
 */
public class DynamicDataSourceContextHolder {


    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * 存储已经注册的数据源的key
     */
    public static List<String> dataSourceIds = new ArrayList<>();

    /**
     * 线程级别的私有变量
     */
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    static String getDataSourceRouterKey() {
        return HOLDER.get();
    }

    public static void setDataSourceRouterKey(String dataSourceRouterKey) {
        logger.info("切换至{}数据源", dataSourceRouterKey);
        HOLDER.set(dataSourceRouterKey);
    }

    /**
     * 设置数据源之前一定要先移除
     */
    public static void removeDataSourceRouterKey() {
        HOLDER.remove();
    }

    /**
     * 判断指定 DataSource 当前是否存在
     */
    @Contract(pure = true)
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

}
