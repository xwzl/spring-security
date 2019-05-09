package com.hdo.web.component;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @ClassName MyLocalResolver
 * @Author XWZ
 * @Description 可以携带区域信息
 * @Date 2018-08-23 15:43 星期四 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
public class MyLocalResolver implements LocaleResolver {


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l=request.getParameter("l");
        //如果l不存在 则使用系统自带的语言信息
        Locale locale=Locale.getDefault();
        if(!StringUtils.isEmpty(l)){
            String[] split=l.split("_");
            //第一参数是国家信息 第二个参数语言信息
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
