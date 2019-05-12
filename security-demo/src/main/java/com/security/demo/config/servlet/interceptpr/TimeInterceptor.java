package com.security.demo.config.servlet.interceptpr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 配置拦截器，用于获取控制器的执行方法的详细信息，但是无法获取参数的详细信息，解决方案 AOP
 *
 * @author xuweizhi
 * @since 2019/05/12 13:47
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(HandlerInterceptor.class);

    /**
     * 前置处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("TimeInterceptor preHandler !");
        HandlerMethod methodHandler = (HandlerMethod) handler;

        //1. 打印控制器的信息
        log.info(methodHandler.getBean().getClass().getName());
        log.info(methodHandler.getMethod().getName());

        request.setAttribute("start-time", System.currentTimeMillis());

        return true;
    }

    /**
     * preHandle 成功执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("TimeInterceptor postHandle !");
        long start = (long) request.getAttribute("start-time");
        log.info("TimeInterceptor 耗时：" + (System.currentTimeMillis() - start));
    }

    /**
     * preHandle 成功执行，且无论 postHandle 都会执行
     *
     * @param ex 注意，没有被全局处理的异常才能获取！
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("TimeInterceptor afterCompletion !");
        long start = (long) request.getAttribute("start-time");
        log.info("TimeInterceptor 耗时：" + (System.currentTimeMillis() - start));
        log.info("TimeInterceptor exception is：" + ex);
    }

}
