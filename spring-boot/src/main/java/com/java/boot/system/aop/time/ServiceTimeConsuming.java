package com.java.boot.system.aop.time;

import com.java.boot.system.annotation.ServiceStatistics;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 * @date 2018/11/15 8:59
 */
@Component
@Aspect
@Order(2)
@Slf4j
public class ServiceTimeConsuming {

    /**
     * 保证每个线程都有一个单独的实例
     */
    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    private boolean flag = false;


    @Before("@annotation(ss)")
    public void before(JoinPoint joinPoint, ServiceStatistics ss) throws NoSuchMethodException {
        threadLocal.set(System.currentTimeMillis());
         //UrlControllerAop.otherLogPrint(joinPoint);
    }

    @AfterReturning("@annotation(ss)")
    public void afterReturning(JoinPoint joinPoint,ServiceStatistics ss) {
        String className = joinPoint.getTarget().getClass().getInterfaces()[0].getSimpleName();
        String methodName =  joinPoint.getSignature().getName() + "  耗时" + ((System.currentTimeMillis() - threadLocal.get())) + "ms";
        log.info("{} 执行方法 {}", className,methodName);
        threadLocal.remove();
    }


}
