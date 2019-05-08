package com.java.boot.base.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author xuweizhi
 * @date 2019/04/24 14:05
 */
@RestController
@RequestMapping("exception")
@Api
public class ExceptionController {


    @GetMapping("test")
    public String getException() {
        Random random = new Random();
        int i = random.nextInt(10);
        if (i > 5) {
            throw new ArithmeticException("随机大于五，用于异常处理");
        }
        return i + "";
    }


}
