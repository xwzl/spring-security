package com.hdo.web.component;

import com.hdo.web.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptors
 * @Author XWZ
 * @Description
 * @Date 2018-08-23 18:29 星期四 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
//继承拦截器之后需要在Configure中配置
public class LoginInterceptors implements HandlerInterceptor {


    //再请求方法前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        //如果有User对象 则不行拦截
        if(user == null){
            request.setAttribute("msg","请先登录，在进行其它操作！");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else {

            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
