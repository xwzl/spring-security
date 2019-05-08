package com.java.boot.system.annotation;

import org.springframework.boot.autoconfigure.condition.OnPropertyListCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * Spring Boot通过@ConditionalOnProperty来控制Configuration是否生效
 * <p>
 * 通过其两个属性name以及havingValue来实现的，其中name用来从application.properties中读取某个属性值。
 * <p>
 * 如果该值为空，则返回false;
 * <p>
 * 如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。
 * <p>
 * 如果返回值为false，则该configuration不生效；为true则生效。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnPropertyListCondition.class)
public @interface ConditionalOnProperty {

    //数组，获取对应property名称的值，与name不可同时使用
    String[] value() default {};

    //property名称的前缀，可有可无
    String prefix() default "";

    //数组，property完整名称或部分名称（可与prefix组合使用，组成完整的property名称），与value不可同时使用
    String[] name() default {};

    //可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
    String havingValue() default "";

    //缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之报错
    boolean matchIfMissing() default false;

    //是否可以松散匹配，至今不知道怎么使用的
    boolean relaxedNames() default true;
}
