package com.java.boot.system.aop;

/**
 * @author xuweizhi
 * @since  2018/11/13 16:38
 */
//@Aspect
//@Component
//@Order(1)
//@Slf4j
public class ControllerAop {

   /* //专业术语
    //1.连接点（JoinPoint）:对用具体的拦截对象，Spring只支持拦截方法，拦截对象是指特定的对象
    //2.切点  （point cut）:有时候需要拦截的方法，不止一个方法而是很多类的方法，这时候需要定义一系列的规则适配
    //3.通知  （advice）   :
    // 前置通知（before advice）
    // 后置通知（after advice）
    // 环绕通知（around advice）
    // 事后返回通知（afterReturning advice）
    // 异常通知（afterThrowing advice）
    //4.切面（aspect）: 是一个可以定义切点，各类通知和引入的内容。

    //@Pointcut("execution(public * com.web.pro.controller.*.*(..))")
    public void pointCut() {
    }

    //@Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取类名
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        //获取切入方法名
        String methodName = joinPoint.getSignature().getName();
        //获取切入点方法参数类型
        Object[] args = joinPoint.getArgs();

        log.info("请求的类名:" + declaringTypeName + ",方法名:" + methodName + ",入参:" + Arrays.toString(args));
    }

    *//*@Around("pointCut()")
    public Object arround(ProceedingJoinPoint pjp) {
        try {
            //调用目标原有的方法
            log.info("环绕通知是一个很强大的通知，使用不当，可能会影响程序的进行");
            Object o = pjp.proceed();
            log.info("方法环绕proceed，结果是 :{}", o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }*//*

    //@AfterThrowing("pointCut()")
    public void throwss(JoinPoint jp) {
        //System.out.println("方法异常时执行.....");
    }

    //无论如何最终都执行
    //@After("pointCut()")
    public void after() {
        //log.info("After Advice={}", "This is a after advice!");
    }

    //@AfterReturning(pointcut = "pointCut()", returning = "object")
    public void getAfterReturn(Object object) {
        //log.info("afterReturning={}", object.toString());
    }

*/
}
