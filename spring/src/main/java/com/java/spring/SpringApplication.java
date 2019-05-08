package com.java.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuweizhi
 * @since 2019/05/08 16:28
 */
@SpringBootApplication
@RestController
public class SpringApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

    @RequestMapping("index")
    public String index() {
        return "Hello Spring !";
    }

}
