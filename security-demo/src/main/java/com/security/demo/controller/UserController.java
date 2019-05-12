package com.security.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.security.demo.dto.User;
import com.security.demo.dto.UserQueryCondition;
import com.security.demo.exception.UserNotExistException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuweizhi
 * @since 2019/05/11 21:29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Pageable 分页参数，设置默认的分页参数
     * <p>
     * 指定在指定的视图展示数据
     */
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@RequestParam(required = false, name = "usernames", defaultValue = "jack") String username, UserQueryCondition condition, @PageableDefault(page = 1, size = 1, sort = "age,desc") Pageable pageable) {
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        System.out.println(pageable);
        return list;
    }

    /**
     * 自动转换为 Json 格式,正则表达式
     * <p>
     * 指定在指定的视图展示数据
     */
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getUserInfo(@PathVariable("id") String id) {
        System.out.println(id);
        if(!id.equals("1")){
            throw new UserNotExistException(id);
        }

        User user = new User();
        user.setUsername("tom");
        return user;
    }

    /**
     * 定义:@requestBody注解常用来处理content-type不是默认的application/x-www-form-urlcoded编码的内容，比如说：
     * application/json或者是application/xml等。一般情况下来说常用其来处理application/json类型.
     * <p>
     * 校验：@Validated 配合 @NotBlank 注解使用，用于校验不为空，与 BindingResult 对象绑定使用，且有错误信息打印。
     */
    @PostMapping
    public User create(@Validated @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user);
        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User updateUser(@Validated @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> {
                //FieldError fieldError = (FieldError) error;
                //// 打印详细的错误信息
                //String msg = fieldError.getField()+":"+fieldError.getDefaultMessage();
                System.out.println(error.getDefaultMessage());
            });
        }
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }


}
