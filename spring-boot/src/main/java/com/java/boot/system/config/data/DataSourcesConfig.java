package com.java.boot.system.config.data;

import com.java.boot.system.aop.data.DynamicDataSourceAnnotationAdvisor;
import com.java.boot.system.aop.data.DynamicDataSourceAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库连接池配置
 *
 * @author xuweizhi
 * @since 2019/04/22 21:48
 */
@Configuration
public class DataSourcesConfig {

    /**
     * 虽然我们配置了druid连接池的其它属性，但是不会生效。因为默认是使用的java.sql.Datasource的类来获取属性的，
     * 有些属性datasource没有。如果我们想让配置生效，需要手动创建Druid的配置文件。
     */
    //@ConfigurationProperties(prefix = "spring.datasource")
    //@Bean
    //public DataSource druidDataSource() {
    //    return new DruidDataSource();
    //}

    //@Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }

    /**
     * Druid的最强大之处在于它有着强大的监控，可以监控我们发送到数据库的所有sql语句。方便我们后期排插错误。
     * <p>
     * 我们接着在DruidDataSource里面配置监控中心:
     * 配置监控服务器
     *
     * @return 返回监控注册的servlet对象
     */
    //@Bean
    //public ServletRegistrationBean statViewServlet() {
    //    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    //    // 添加IP白名单
    //    //servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
    //    // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
    //    //servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
    //    // 添加控制台管理用户
    //    servletRegistrationBean.addInitParameter("loginUsername", "root");
    //    servletRegistrationBean.addInitParameter("loginPassword", "123456");
    //    // 是否能够重置数据
    //    servletRegistrationBean.addInitParameter("resetEnable", "false");
    //    return servletRegistrationBean;
    //}

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    //@Bean
    //public FilterRegistrationBean statFilter() {
    //    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
    //    // 添加过滤规则
    //    filterRegistrationBean.addUrlPatterns("/*");
    //    // 忽略过滤格式
    //    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
    //    return filterRegistrationBean;
    //}
}
