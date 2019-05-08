package com.java.boot.base.controller;

import com.java.boot.base.model.User;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author xuweizhi
 * @since 2019/04/22 14:10
 */
@Api
@RestController
public class LoginController {

    /**
     * 描述：session对象
     */
    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public ResponseEntity<Void> login(User user) {
        //1 接收页面参数，转成对象----系统自动完成了
        //2 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //3 Subject启动Shiro
        // 准备数据
        UsernamePasswordToken upToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(upToken);
            // 能够执行到这步，肯定登录已经成功
            // 获取用户信息，保存到session中
            User loginUser = (User) subject.getPrincipal();
            // 放入session中
            session.setAttribute("loginUser", loginUser);
            //返回状态
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (UnknownAccountException uae) {
            //用户名未知...
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException ice) {
            //凭据不正确，例如密码不正确 ...
            System.out.println("密码不正确");
        } catch (LockedAccountException lae) {
            //用户被锁定，例如管理员把某个用户禁用...
            System.out.println("用户被禁用");
        } catch (ExcessiveAttemptsException eae) {
            //尝试认证次数多余系统指定次数 ...
            System.out.println("请求次数过多，用户被锁定");
        } catch (AuthenticationException ae) {
            //其他未指定异常
            System.out.println("未知错误，无法完成登录");
        }
        //返回状态
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
