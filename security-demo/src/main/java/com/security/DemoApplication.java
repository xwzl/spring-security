package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/swagger-ui.html
 *
 * WireMock 伪造 Restful 风格的 API
 *
 * http://wiremock.org/docs/running-standalone/
 *
 * @author xuweizhi
 * @since 2019/05/11 20:42
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(value={"com.security.core","com.security.browser"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
