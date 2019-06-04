package com.data.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 不可用
 *
 * @author xuweizhi
 * @since 2019/06/04 14:41
 */
@SpringBootApplication
@EnableTransactionManagement
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}
