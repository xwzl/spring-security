package com.java.boot.system.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 由于我们在前后端分离中集成了shiro，因此需要在headers中自定义一个'Authorization'字段，此时普通的GET、POST等请求会变成
 * preflighted request，即在GET、POST请求之前会预先发一个OPTIONS请求
 * <p>
 * https://blog.csdn.net/cc1314_/article/details/78272329
 *
 * @author xuweizhi
 * @since 2019/04/29 16:13
 */
public class CommonIntercepter implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //允许跨域,不能放在postHandle内
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (request.getMethod().equals("OPTIONS")) {
            response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH");
            // Authorization 用于 Shiro 验证
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
        }
        return true;
    }
}
