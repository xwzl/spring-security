package com.java.boot;

import com.java.boot.system.config.data.DataSourcesConfig;
import com.java.boot.system.config.data.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 使用 @SpringBootApplication 注解，标明是 Spring Boot 应用。通过它，可以开启自动配置的功能
 * <p>
 * {@link SpringApplication} ，Spring 应用启动器。正如其代码上所添加的注释，它来提供启动 Spring 应用的功能。
 * <p>
 * EnableCaching redis 缓存， 数据库事务
 *
 * <h2>数据源切换注意事项</h2>
 * SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
 * <p>
 * 测试数据源的时候，最好把 Redis 缓存关掉，即注释掉相关的Redis缓存注解
 * <h3>druid 数据源切换：</h3>
 * <ul>
 * <li>{@link SpringBootRun} 注解@Import(DynamicDataSourceRegister.class) 注释</li>
 * <li>{@link DataSourcesConfig#dynamicDatasourceAnnotationAdvisor} 注解@Bean 注释</li>
 * <li>application.yml include 换成druid1</li>
 * </ul>
 * <h3>hikari 多数据源切换：</h3>
 * 与上面相反，application.yml include hikari
 * <h3>druid 多数据源切换：</h3>
 * 与上面相反，application.yml include 换成druid2 无法监控 sql
 *
 * @author xuweizhi
 * @date 2019/04/22 10:50
 */
@EnableCaching
@Import(DynamicDataSourceRegister.class)
@EnableTransactionManagement
@MapperScan(basePackages = "com.java.boot.base.mapper")
@SpringBootApplication
public class SpringBootRun {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRun.class, args);
    }


    //public ConfigurableApplicationContext run(String... args) {
    //    // <1> 创建 StopWatch 对象，并启动。StopWatch 主要用于简单统计 run 启动过程的时长。
    //    StopWatch stopWatch = new StopWatch();
    //    stopWatch.start();
    //    //
    //    ConfigurableApplicationContext context = null;
    //    Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
    //    // <2> 配置 headless 属性
    //    configureHeadlessProperty();
    //    // 获得 SpringApplicationRunListener 的数组，并启动监听
    //    SpringApplicationRunListeners listeners = getRunListeners(args);
    //    listeners.starting();
    //    try {
    //        // <3> 创建  ApplicationArguments 对象
    //        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
    //        // <4> 加载属性配置。执行完成后，所有的 environment 的属性都会加载进来，包括 application.yml 和外部的属性配置。
    //        ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
    //        configureIgnoreBeanInfo(environment);
    //        // <5> 打印 Spring Banner
    //        Banner printedBanner = printBanner(environment);
    //        // <6> 创建 Spring 容器。
    //        context = createApplicationContext();
    //        // <7> 异常报告器
    //        exceptionReporters = getSpringFactoriesInstances(
    //                SpringBootExceptionReporter.class,
    //                new Class[] { ConfigurableApplicationContext.class }, context);
    //        // <8> 主要是调用所有初始化类的 initialize 方法
    //        prepareContext(context, environment, listeners, applicationArguments,
    //                printedBanner);
    //        // <9> 初始化 Spring 容器。
    //        refreshContext(context);
    //        // <10> 执行 Spring 容器的初始化的后置逻辑。默认实现为空。
    //        afterRefresh(context, applicationArguments);
    //        // <11> 停止 StopWatch 统计时长
    //        stopWatch.stop();
    //        // <12> 打印 Spring Boot 启动的时长日志。
    //        if (this.logStartupInfo) {
    //            new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
    //        }
    //        // <13> 通知 SpringApplicationRunListener 的数组，Spring 容器启动完成。
    //        listeners.started(context);
    //        // <14> 调用 ApplicationRunner 或者 CommandLineRunner 的运行方法。
    //        callRunners(context, applicationArguments);
    //    } catch (Throwable ex) {
    //        // <14.1> 如果发生异常，则进行处理，并抛出 IllegalStateException 异常
    //        handleRunFailure(context, ex, exceptionReporters, listeners);
    //        throw new IllegalStateException(ex);
    //    }
    //
    //    // <15> 通知 SpringApplicationRunListener 的数组，Spring 容器运行中。
    //    try {
    //        listeners.running(context);
    //    } catch (Throwable ex) {
    //        // <15.1> 如果发生异常，则进行处理，并抛出 IllegalStateException 异常
    //        handleRunFailure(context, ex, exceptionReporters, null);
    //        throw new IllegalStateException(ex);
    //    }
    //    return context;
    //}

    //private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments) {
    //    // Create and configure the environment
    //    // <1> 创建 ConfigurableEnvironment 对象，并进行配置
    //    ConfigurableEnvironment environment = getOrCreateEnvironment();
    //    configureEnvironment(environment, applicationArguments.getSourceArgs());
    //    // <2> 通知 SpringApplicationRunListener 的数组，环境变量已经准备完成。
    //    listeners.environmentPrepared(environment);
    //    // <3> 绑定 environment 到 SpringApplication 上
    //    bindToSpringApplication(environment);
    //    // <4> 如果非自定义 environment ，则根据条件转换
    //    if (!this.isCustomEnvironment) {
    //        environment = new EnvironmentConverter(getClassLoader()).convertEnvironmentIfNecessary(environment, deduceEnvironmentClass());
    //    }
    //    // <5> 如果有 attach 到 environment 上的 MutablePropertySources ，则添加到 environment 的 PropertySource 中。
    //    ConfigurationPropertySources.attach(environment);
    //    return environment;
    //}

}
