package com.security.config.servlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * 方法调用过滤器，由于这是 JavaEE2 的规范，无法获取与 MVC 相关的信息即控制器，因此配置拦截器，
 * 获取当前方法调用的控制器。
 *
 * 注解 @Component 适用于自带的Filter ,第三方的需要在 Configuration 中单独配置
 *
 * @author xuweizhi
 * @since 2019/05/12 13:26
 */
//@Component
public class TimeFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(TimeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("time filter init !");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       log.info("time filter start !");
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        log.info("time filter:"+(System.currentTimeMillis()-start));
       log.info("time filter finish !");
    }

    @Override
    public void destroy() {
         log.info("time filter destory !");
    }
}
