package com.java.boot.system.annotation;

import java.lang.annotation.*;

/**
 * 切换数据注解 可以用于类或者方法级别 方法级别优先级 > 类级别
 * <p>
 * 现在spring也已经知道通过key来取对应的数据源，我们现在只需要实现给对应的类或者方法设置他们的数据源的key，并且保存在数据源上下文中即可。
 * 这里我们采用注解来设置数据源，通过aop拦截并且保存到数据源上下中。
 *
 * @author xuweizhi
 * @since 2019/04/25 10:07
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 其中他的默认值是master,因为我们默认数据源的key也是master。也就是说如果你直接用注解，而不指定value的话，
     * 那么默认就使用master默认数据源。
     */
    String value() default "master";

}
