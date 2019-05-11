package com.java.controller;

import com.java.dto.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuweizhi
 * @since 2019/05/11 21:29
 */
@RestController
public class UserController {

    @RequestMapping(value="user",method = RequestMethod.GET)
    public List<User> query(){
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }
}
