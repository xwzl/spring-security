package com.java.boot.system.config.other;

import org.springframework.context.annotation.Configuration;

/**
 * 配置文件注入
 * <p>
 * 必须加@Component 和 @EnableAutoConfiguration注解
 *
 * @author xuweizhi
 * @date 2019/04/22 16:02
 */
@Configuration
public class PropertyConfigure {

    ///**
    // * 激活配置文件
    // *
    // */
    //@Bean
    //public static PropertySourcesPlaceholderConfigurer properties() {
    //    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
    //    YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
    //    //File引入
    //    //yaml.setResources(new FileSystemResource("config.yml"));
    //    //class引入
    //    yaml.setResources(new ClassPathResource("application-druid1.yml"));
    //    configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
    //    return configurer;
    //}

}
