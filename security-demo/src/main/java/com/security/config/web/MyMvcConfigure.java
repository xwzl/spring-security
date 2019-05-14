package com.security.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author xuweizhi
 * @since  2019-5-14 10:14
 */
@Configuration
public class MyMvcConfigure implements WebMvcConfigurer {

    /**
     * 添加指定目录下的资源
     */
    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    registry.addResourceHandler("/**")
    //        .addResourceLocations("classpath:/resources/")
    //        .addResourceLocations("classpath:/static/");
    //    WebMvcConfigurer.super.addResourceHandlers(registry);
    //}
}
