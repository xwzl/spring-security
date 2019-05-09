package com.hdo.web.controller;

import com.hdo.web.component.UserNotExistException;
import com.hdo.web.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DemoController
 * @Author XWZ
 * @Description
 * @Date 2018-08-22 16:58 星期三 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
@Controller
public class DemoController {

    //没有配置模板引擎 默认配置访问Resources目录下的Public文件下的静态页面
    //已经在配置文件中配置
    /*@RequestMapping({"/","/index.html"})
    public String toLogin(){
        return "index";
    }*/

    @RequestMapping("/index1")
    @ResponseBody
    public String toIndex(){
        return "xxxxxxxxxxx";
    }

    @RequestMapping("/fastJson")
    @ResponseBody
    public Student toFastJson(){
        Student st=new Student();
        st.setName("张三");
        st.setPass("11");
        st.setDate(new Date());
        return st;
    }

    @RequestMapping("/mapper")
    public String toMapper( Map<String,Object> map){
        map.put("hello","<h1>你好啊！大笨蛋！</h1>");
        return "success";
    }

    @RequestMapping("/js")
    public String toJs(){
        return "javascript/demo";
    }

    @RequestMapping("/user")
    @ResponseBody
    public String toUser(String user){
            if(user != null && user.equals("aaa")){
              throw  new UserNotExistException();
             }
             return "Hello Exception!";
    }

    @RequestMapping("/hello")
    public String toHelloJsp(){
        return "Hello";
    }
}
