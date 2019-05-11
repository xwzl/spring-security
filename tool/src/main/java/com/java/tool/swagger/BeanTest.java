package com.java.tool.swagger;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 测试Bean
 *
 * @author xuweizhi
 * @date 2019/03/26 15:30
 */
@Component
@Data
@ConfigurationProperties(prefix = "user")
public class BeanTest {

    private String name1;

    private String age;

    public BeanTest() {
    }


}
