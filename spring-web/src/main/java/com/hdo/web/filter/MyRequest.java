package com.hdo.web.filter;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: XWZ
 * @Date: Create In 2018/8/10 20:31: 星期五 web
 * @Description:增强request 过滤编码
 */
public class MyRequest extends HttpServletRequestWrapper {

    HttpServletRequest request=null;

    public MyRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {

        String method = request.getMethod();
        String value=null;
        try {
            request.setCharacterEncoding("UTF-8");
            value = request.getParameter(name);
            if(!StringUtils.isEmpty(value)){
                if("get".equalsIgnoreCase(method)){
                    value = new String(value.getBytes("iso-8859-1"),"UTF-8");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    @Override
    public String[] getParameterValues(String name) {

        String method = request.getMethod();
        String[] values=null;
        try {
            request.setCharacterEncoding("utf-8");
            values = request.getParameterValues(name);
            if("get".equalsIgnoreCase(method)){
                int i=0;
                for (String str : values) {
                    values[i++] = new String(str.getBytes("iso-8859-1"),"utf-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return values;
    }

}

