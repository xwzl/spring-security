package com.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.security.core.proterties.SecurityProperties;
import com.security.dto.User;
import com.security.dto.UserQueryCondition;
import com.security.exception.UserNotExistException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuweizhi
 * @since 2019/05/11 21:29
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    /**
     * 工具类，从session 中获取消息
     */
    //@Autowired
    //private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * app 端用这个
     */
    @Autowired
    //private AppSingUpUtils appSingUpUtils;

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {
        // 不管是注册用户还是绑定用户，都会拿到一个用户唯一标识
        String userId = user.getUsername();
        // 做一个查询的动作,向数据库中的表 UserConnection 中插入数据
        //providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
        //appSingUpUtils.doPostSignUp(new ServletWebRequest(request),userId);
    }


    /**
     * 获取当前用户的身份信息
     */
    @GetMapping("/me")
    public UserDetails getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
        //return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/me1")
    public Authentication getCurrentUsers(Authentication user,HttpServletRequest request) throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        // 获取 token
        String token = StringUtils.substringAfter(header, "bearer ");
        // 验证签名信息
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8")).
                parseClaimsJws(token).getBody();
        String company =(String) claims.get("company");
        log.info(company);
        return user;
        //return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Pageable 分页参数，设置默认的分页参数
     * <p>
     * 指定在指定的视图展示数据
     * <p>
     * 指定方法名
     */
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "获取用户详情！")
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
        if (!id.equals("1")) {
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

    /**
     * 设置参数id
     */
    @DeleteMapping("/{id:\\d+}")
    public void delete(@ApiParam("用户id") @PathVariable String id) {
        System.out.println(id);
    }


}
