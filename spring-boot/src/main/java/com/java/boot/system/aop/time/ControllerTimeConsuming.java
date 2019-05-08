package com.java.boot.system.aop.time;

import com.java.boot.system.annotation.ControllerStatistics;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author xuweizhi
 * @since 2018/11/15 8:59
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class ControllerTimeConsuming {

    /**
     * 保证每个线程都有一个单独的实例
     */
    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    private boolean flag = false;


    @Before("@annotation(cs)")
    public void before(@NotNull JoinPoint joinPoint, ControllerStatistics cs) throws NoSuchMethodException {
        threadLocal.set(System.currentTimeMillis());
        //获取拦截的方法名
        Signature sig = joinPoint.getSignature();
        log.info(joinPoint.getTarget().getClass().getSimpleName() + "#" + joinPoint.getSignature().getName() + " 开始执行！");
        //logPrint(joinPoint);
    }

    @AfterReturning("@annotation(cs)")
    public void afterReturning(JoinPoint joinPoint, ControllerStatistics cs) {
        log.info(joinPoint.getTarget().getClass().getSimpleName()+" 执行方法 "+joinPoint.getSignature().getName()+ " 耗时 {}", ((System.currentTimeMillis() - threadLocal.get())) + "ms");
        threadLocal.remove();
    }

    public void logPrint(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = attributes.getRequest();
        //log.info("请求地址: {}", request.getRequestURL().toString() + " 请求类型:" + request.getMethod());
        //log.info("请求方式: {}", request.getMethod());
        //log.info("IP: {}", request.getRemoteAddr());
        //log.info("User-Agent:{}", request.getHeader("User-Agent"));
        //log.info("Cookies:{}", request.getCookies());
        //log.info("Controller请求方法:{}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //log.info("Params:{}", Arrays.toString(joinPoint.getArgs()));
        //Enumeration<String> enums = request.getParameterNames();
        //
        //log.info("Controller请求参数:{}", );
    }

}
