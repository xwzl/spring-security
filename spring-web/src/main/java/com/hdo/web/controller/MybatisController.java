package com.hdo.web.controller;

import com.hdo.web.model.Loginnum;
import com.hdo.web.service.LoginnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName MybatisController
 * @Author XWZ
 * @Description
 * @Date 2018-08-28 22:50 星期二 SpringBootProgram
 * @VERSION 1.0.0
 **/
@Controller
public class MybatisController {

    @Autowired
    private LoginnumService loginnumService;

    @RequestMapping("/insert")
    @ResponseBody
    public Loginnum insertLoginnum(Loginnum loginnum){
        System.out.println(loginnum);
        loginnumService.insertDerp(loginnum);
        return  loginnum;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public int deleteById(@PathVariable("id")Integer id){
       return  loginnumService.deleteLoginnumById(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Loginnum updateLoginnum(Loginnum loginnum){
        loginnumService.updateLoginnum(loginnum);
        return loginnum;
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    public Loginnum getLoginnum(@PathVariable("id")Integer id){
        Loginnum loginnum=loginnumService.getLoginnumByid(id);
        return loginnum;
    }

}
