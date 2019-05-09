package com.hdo.web.configure;

import com.hdo.web.component.LoginInterceptors;
import com.hdo.web.component.MyLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyMvcConfig
 * @Author XWZ
 * @Description
 * @Date 2018-08-22 17:01 星期三 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
//@EnableWebMvc Spring自动配置将全面失效
@Configuration
public class MyMvcConfigure implements WebMvcConfigurer {

    /**
     * 配置拦截路径 设置返回路径
     * 所有视图拦截都会有效果
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main.html").setViewName("main");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index").setViewName("login");
    }

    //国际化语言设置
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocalResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有的请求/是基础项目下的请求 除开默认登录的首页或者请求登录的方法
        //放弃拦截所有静态资源
        List<String> list=new ArrayList<>();
        list.add("/");
        list.add("/index.html");
        list.add("/user/login");
        list.add("/index.html");
        list.add("/webjars/**");
        list.add("/asserts/**");
        list.add("/static/**");
        list.add("/sql/**");
        list.add("/resources/**");
        registry.addInterceptor(new LoginInterceptors()).addPathPatterns("/**")
                .excludePathPatterns(list);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/META-INF/resources/")
            .addResourceLocations("classpath:/resources/")
            .addResourceLocations("classpath:/static/")
            .addResourceLocations("classpath:/public/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
