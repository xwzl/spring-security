package com.java.boot.system.annotation;

import java.lang.annotation.*;

/**
 * @author xuweizhi
 * @since  2019/04/30 18:43
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MapperStatistics {
}
