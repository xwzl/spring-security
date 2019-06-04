package com.data.dynamic.config;

import com.data.dynamic.enums.DataSourceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 该类为数据源上下文配置，用于切换数据源
 *
 * @author xuweizhi
 * @since 2019/06/04 14:54
 */
public class DynamicDataSourceContextHolder {

    private static final Logger log = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    private static Lock lock = new ReentrantLock();

    private static int counter = 0;

    private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.master::getSource);

    public static List<Object> dataSourceKeys = new ArrayList<>();

    public static List<Object> slaveDataSourceKeys = new ArrayList<>();

    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    public static void useMasterDataSource() {
        CONTEXT_HOLDER.set(DataSourceKey.master.getSource());
    }

    public static void useSlaveDataSource() {
        lock.lock();
        try {
            int dataSourceKeyIndex = counter % slaveDataSourceKeys.size();
            CONTEXT_HOLDER.set(String.valueOf(slaveDataSourceKeys.get(dataSourceKeyIndex)));
            counter++;
        } catch (Exception e) {
            log.error("Switch slave datasource failed, error message is {}", e.getMessage());
            useMasterDataSource();
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }


    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }


    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);

    }


}
