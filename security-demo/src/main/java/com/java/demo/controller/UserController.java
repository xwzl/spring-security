package com.java.demo.controller;

import com.java.demo.dto.User;
import com.java.demo.dto.UserQueryCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuweizhi
 * @since 2019/05/11 21:29
 */
@RestController
public class UserController {

    @RequestMapping(value="/test",method = RequestMethod.GET)
    public List<User> query(@RequestParam(required = false,name = "usernames",defaultValue = "jack") String username, UserQueryCondition condition, @PageableDefault(page = 1,size = 1,sort = "age,desc") Pageable pageable){
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        System.out.println(pageable);
        return list;
    }
}
