package com.security.demo.service.impl;

import com.security.demo.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author xuweizhi
 * @since 2019/05/12 10:12
 */
@Service
public class HelloServiceImpl implements HelloService {


    @Override
    public String helloService(String name) {
        System.out.println("greeting");
        return "Hello Service";
    }
}
