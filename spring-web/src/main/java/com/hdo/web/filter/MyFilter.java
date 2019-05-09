package com.hdo.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyFilter
 * @Author XWZ
 * @Description
 * @Date 2018-08-26 15:18 星期日 SpringBootProgram
 * @VERSION 1.0.0
 **/
//@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {
    //设置默认的字符编码
    private String defaultCharset = "UTF-8";
    //Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //logger.info("Web MyFilterInitialized ...................");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding(defaultCharset);
        response.setCharacterEncoding(defaultCharset);
        response.setContentType("text/html;charset="+defaultCharset);
        MyCharacterEncodingRequest requestWrapper = new MyCharacterEncodingRequest(request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
        //logger.info("Web MyFilter destroy ...................");
    }
}
