package com.java.boot.system.config.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源路由
 * <p>
 * 前面我们以及新建了数据源上下文，用于存储我们当前线程的数据源key那么怎么通知spring用key当前的数据源呢，查阅资料可知，
 * spring提供一个接口，名为AbstractRoutingDataSource的抽象类，我们只需要重写determineCurrentLookupKey方法就可以，
 * 这个方法看名字就知道，就是返回当前线程的数据源的key，那我们只需要从我们刚刚的数据源上下文中取出我们的key即可.
 *
 * @author xuweizhi
 * @since 2019/04/25 11:02
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static Logger logger = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceRouterKey();
        logger.debug("当前数据源是：{}", dataSourceName == null ? "master" : dataSourceName);
        return DynamicDataSourceContextHolder.getDataSourceRouterKey();
    }
}
