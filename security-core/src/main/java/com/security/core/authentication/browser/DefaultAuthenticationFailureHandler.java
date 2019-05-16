package com.security.core.authentication.browser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.core.proterties.LoginType;
import com.security.core.support.SimpleResponse;
import com.security.core.proterties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理方法
 *
 * @author xuweizhi
 * @date 2019/05/15 9:32
 */
@Component("defaultAuthenticationFailureHandler")
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * Json 工具转换类
     */
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败!");
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            // 以 json 格式返回认证信息
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));

        }else{
            // 父类的方法就是跳转
            super.onAuthenticationFailure(request, response, exception);
        }

    }

}
