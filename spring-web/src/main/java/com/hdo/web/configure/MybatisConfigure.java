package com.hdo.web.configure;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName MybatisConfigure
 * @Author XWZ
 * @Description
 * @Date 2018-09-02 21:43 星期日 SpringBootProgram
 * @VERSION 1.0.0
 **/
@org.springframework.context.annotation.Configuration
public class MybatisConfigure {

    //驼峰命名法
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){
            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }

}
