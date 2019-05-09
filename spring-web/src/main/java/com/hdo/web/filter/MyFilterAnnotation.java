package com.hdo.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class MyFilterAnnotation implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 利用装饰者模式统一处理中文乱码
     * 特别注意 post和get请求 处理乱码方式不一样
     * @param request 不同请百度
     * @param response ....
     * @param chain 过滤器引用或者句柄，用来处理客服端请求的request和response相应
     * @throws IOException 数据传输途中出现的异常
     * @throws ServletException 服务端异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest=(HttpServletRequest) request;
        HttpServletResponse servletResponse=(HttpServletResponse) response;
        System.out.println("==========================");
        MyRequest myRequest=new MyRequest(servletRequest);
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=utf-8");
        /* servletResponse.setContentType("application/xml;charset=utf-8");*/
        chain.doFilter(myRequest, response);
    }

    @Override
    public void destroy() {

    }
}
