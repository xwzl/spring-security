package com.hdo.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1.通过继承HttpServlet 在boot上配置注解方式过滤
 *   1.1 WebServlet(urlPatterns = "/MyServlet")
 *   1.2 ServletComponentScan(basePackages = {"com.boot.web.servlet","com.boot.web.filter"})
 * 2.通过继承extends HttpServlet，配置configure
 *   2.1
 *
 */
@WebServlet(urlPatterns = "/MyServlet")
public class MyServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            /*resp.setContentType("text/html;charset=utf-8");*/
            resp.getWriter().print("你是一个智障");
            resp.getWriter().flush();
            resp.getWriter().close();
    }
}
