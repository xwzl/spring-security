package com.java.boot.system.config.other;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

/**
 * Swagger2 配置类
 * <p>
 * swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
 * <ul>
 * <li>@Api：修饰整个类，描述Controller的作用</li>
 * <li>@ApiOperation：描述一个类的一个方法，或者说一个接口</li>
 * <li>@ApiParam：单个参数描述</li>
 * <li>@ApiModel：用对象来接收参数</li>
 * <li>@ApiProperty：用对象接收参数时，描述对象的一个字段</li>
 * <li>@ApiResponse：HTTP响应其中1个描述</li>
 * <li>@ApiResponses：HTTP响应整体描述</li>
 * <li>@ApiIgnore：使用该注解忽略这个API</li>
 * <li>@ApiError ：发生错误返回的信息</li>
 * <li>@ApiParamImplicitL：一个请求参数</li>
 * <li>@ApiParamsImplicit 多个请求参数</li>
 * </ul>
 * <p>
 * http://localhost:8082/spring-boot/swagger-ui.html#/ 访问
 *
 * @author xuweizhi
 * @date 2019/04/23 14:24
 */
@Configuration
@Component
@EnableSwagger2
public class SwaggerConfigure {

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String address;

    @Value("${server.servlet.context-path}")
    private String context;

    @SuppressWarnings("deprecation")
    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = input -> {
            Class<?> declaringClass = input.declaringClass();
            // 排除
            if (declaringClass == BasicErrorController.class) {
                return false;
            }
            // 被注解的类
            if (declaringClass.isAnnotationPresent(RestController.class)) {
                return true;
            }
            // 被注解的方法
            return input.isAnnotatedWith(ResponseBody.class);
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate::test)
                .build();
    }

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://");
        if (StringUtils.isEmpty(address)) {
            sb.append("127.0.0.1:");
        } else {
            sb.append(address).append(":");
        }
        if (StringUtils.isEmpty(port)) {
            sb.append("8080");
        } else {
            sb.append(port);
        }
        if (!StringUtils.isEmpty(context)) {
            String prefix = "/";
            if (context.startsWith(prefix)) {
                sb.append(context);
            } else {
                sb.append(prefix).append(context);
            }
        }
        String url = sb.toString();
        return new ApiInfoBuilder()
                //大标题
                .title("swagger-bootstrap-ui RESTful APIs")
                .description("swagger-bootstrap-ui")
                .termsOfServiceUrl(url)
                .contact("--")
                //版本
                .version("1.0")
                .build();
    }

}
