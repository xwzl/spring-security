package com.java.tool.untitl.poi;

import java.lang.annotation.*;

/**
 * @author xuweizhi
 * @date 2019/03/13 9:27
 * @description
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
public @interface IsNeed {

    String value() default "";

    int type() ;
}

