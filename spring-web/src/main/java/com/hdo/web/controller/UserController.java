package com.hdo.web.controller;


import com.hdo.web.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName UserController
 * @Author XWZ
 * @Description
 * @Date 2018-08-23 16:35 星期四 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    //RequestParam 默认参数必须有 没有则会报错
    @PostMapping("/login")
    public String toMain(@RequestParam("username")String username, @RequestParam("password")String password,
                         Map<String, Object> map, HttpServletRequest request){
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            User user=new User();
            user.setPassword(password);
            user.setUsername(username);
            request.getSession().setAttribute("user",user);
            logger.info("username:"+user.getUsername()+" password:"+user.getPassword());
            return "redirect:/main.html";
        }else{
            map.put("msg","密码错误！");
            return "login";
        }

    }
}
