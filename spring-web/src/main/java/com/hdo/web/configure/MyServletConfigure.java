package com.hdo.web.configure;

import com.hdo.web.filter.MyFilter;
import com.hdo.web.listener.MyListener;
import com.hdo.web.servlet.MyServlet;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @ClassName MyServletConfigure
 * @Author XWZ
 * @Description
 * @Date 2018-08-26 15:25 星期日 SpringBootProgram
 * @VERSION 1.0.0
 **/

@Configuration
public class MyServletConfigure {

    //注册三大组件
    @Bean
    public ServletRegistrationBean myServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(),"/myServlet");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        //*registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));*//*
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
        return registrationBean;
    }

    //2.x
    @Configuration
    public class TomcatCustomizer {

        @Bean
        public ConfigurableServletWebServerFactory configurableServletWebServerFactory(){
            TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
            factory.setPort(8083);
            return factory;
        }
    }

    /*//配置嵌入式的Servlet容器 1.x
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            //定制嵌入式的Servlet容器相关的规则
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setPort(8083);
            }
        };
    }*/
}
