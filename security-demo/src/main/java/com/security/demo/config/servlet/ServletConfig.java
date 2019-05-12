package com.security.demo.config.servlet;

import com.security.demo.config.servlet.filter.TimeFilter;
import com.security.demo.config.servlet.interceptpr.TimeInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置第三方的拦截
 *
 * @author xuweizhi
 * @since 2019/05/12 13:38
 */
//@Configuration
public class ServletConfig implements WebMvcConfigurer {

    private final TimeInterceptor timeInterceptor;

    public ServletConfig(TimeInterceptor timeInterceptor) {
        this.timeInterceptor = timeInterceptor;
    }

    ///**
    // * 注册异步配置
    // */
    //@Override
    //public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    //    configurer.registerCallableInterceptors()
    //}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    /**
     * 配置第三方拦截的 filter bean,方法名为 Bean 名称
     * 拦截所有的请求路径
     */
    @Bean
    public FilterRegistrationBean<TimeFilter> timeFilter() {

        FilterRegistrationBean<TimeFilter> registrationBean = new FilterRegistrationBean<>();

        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);

        List<String> urls = new ArrayList<>();
        // 所有路径都起作用
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }


}
