package com.hdo.web.component;

/**
 * @ClassName UserNotExistException
 * @Author XWZ
 * @Description
 * @Date 2018-08-26 15:34 星期日 SpringBootProgram
 * @VERSION 1.0.0
 **/
public class UserNotExistException extends RuntimeException {

    public UserNotExistException(){
        super("用户不存在！");
    }

}
