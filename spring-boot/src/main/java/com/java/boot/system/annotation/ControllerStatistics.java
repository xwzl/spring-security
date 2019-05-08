package com.java.boot.system.annotation;

import java.lang.annotation.*;

/**
 * @author xuweizhi
 * @since  2019/04/30 18:43
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerStatistics {
    /**
     * 其中他的默认值是master,因为我们默认数据源的key也是master。也就是说如果你直接用注解，而不指定value的话，
     * 那么默认就使用master默认数据源。
     */
    String value() default "master";
}
