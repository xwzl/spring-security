package com.security.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Controller 函数执行切面
 * <p>
 * 其中 @Before 注解相当于 HandlerInterceptor 接口的 preHandler,@After 相当于 HandlerInterceptor 接口
 * 的 afterCompletion 方法，@Throws ......
 * <p>
 * 常用 @Around 注解，包括方法的所有信息
 *
 * @author xuweizhi
 * @since 2019/05/12 14:17
 */
@Aspect
@Component
public class TimeAspect {

    private final Logger log = LoggerFactory.getLogger(TimeAspect.class);

    /**
     * 正则表达式的意思是，环切 com.security.demo.controller 任意方法，任意参数，任意返回值
     */
    @Pointcut("execution(* com.security.demo.controller.*.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object handleControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("TimeAspect start !");
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            Stream.of(args).forEach(arg -> {
                log.info("Arg is :" + arg);
            });
        }
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        log.info("TimeAspect 耗时：" + (System.currentTimeMillis() - start));
        log.info("TimeAspect finish !");
        return proceed;
    }
}
