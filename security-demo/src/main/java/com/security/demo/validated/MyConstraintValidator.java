package com.security.demo.validated;

import com.security.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 专门用于数据校验的接口
 * A: 表示需要校验的注解
 * T: 表示需要校验的是成员变量的数据类型，Object 表示成员变量的类型为任意类型
 *
 * @author xuweizhi
 * @since 2019/05/12 10:05
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    /**
     * 不用写类注解，直接可注入
     */
    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("My validator initialize !");
    }

    /**
     * 自定义校验逻辑
     *
     * @param o                          传进来的值
     * @param constraintValidatorContext 注解的原信息
     * @return true表示校验通过
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(o);
        helloService.helloService("tom");
        return false;
    }
}
