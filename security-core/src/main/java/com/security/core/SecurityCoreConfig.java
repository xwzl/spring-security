package com.security.core;

import com.security.core.proterties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 让 SecurityProperties 配置其效果
 *
 * @author xuweizhi
 * @date 2019/05/15 1:07
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
