package com.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuweizhi
 * @since 2019/05/08 16:28
 */
@SpringBootApplication
@RestController
public class JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaApplication.class, args);
    }

    @RequestMapping("index")
    public String index() {
        return "Hello Spring !";
    }

}
