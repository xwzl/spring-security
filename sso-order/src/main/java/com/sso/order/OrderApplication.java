package com.sso.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证服务器
 *
 * @author xuweizhi
 * @date 2019/06/11 23:51
 */
@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}