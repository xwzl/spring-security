package com.java.boot.system.aop.data;


import com.java.boot.system.config.data.DataSourcesConfig;
import com.java.boot.system.config.data.DynamicDataSourceContextHolder;
import com.java.boot.system.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 其中他的默认值是master,因为我们默认数据源的key也是master。也就是说如果你直接用注解，而不指定value的话，那么
 * 默认就使用master默认数据源。
 * <p>
 * 不起作用了，使用 AOP 关闭 {@link DataSourcesConfig#dynamicDatasourceAnnotationAdvisor()} @bena
 *
 * @author xuweizhi
 * @since 2019/04/25 10:07
 */
@Aspect
@Component
@Order(-1900)
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, @NotNull DataSource ds) throws Throwable {
        String dsId = ds.value();
        if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
            logger.info("Use DataSource :{} >", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        } else {
            logger.info("数据源[{}]不存在，使用默认数据源 >{}", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(@NotNull JoinPoint point, @NotNull DataSource ds) {
        logger.debug("Revert DataSource : " + ds.value() + " > " + point.getSignature());
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();

    }
}