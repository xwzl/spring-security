package com.hdo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@MapperScan(basePackages = "com.product.web.mapper")
@EnableTransactionManagement//开启事务管理
//@ServletComponentScan(basePackages = {"com.product.web.servlet", "com.product.web.filter"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}




