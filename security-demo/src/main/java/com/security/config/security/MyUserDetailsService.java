package com.security.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * UserDetailsService 处理表单的登录结果
 * SocialUserDetails 处理第三方认证
 *
 * @author xuweizhi
 * @date 2019/05/14 1:04
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    private final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    /**
     * 正常情况注入数据查询 Mapper 获取密码
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名认证用户，表单登录的时候使用
     * 使用springboot，权限管理使用spring security，使用内存用户验证，但无响应报错：
     * java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
     * <p>
     * 解决方法：
     * 这是因为Spring boot 2.0.3引用的security 依赖是 spring security 5.X版本，此版本需要提供一个PasswordEncoder的实例，
     * <p>
     * 否则后台汇报错误：
     * java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"并且页面毫无响应。
     * 因此，需要创建PasswordEncoder的实现类。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户登录名是：" + username);
        return getUserDetails(username);
    }


    /**
     * 社交登录使用，第三方登录,userId 就是 opendId
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("第三方登录名是：" + userId);
        return getUserDetails(userId);
    }

    private SocialUserDetails getUserDetails(String username) {
        // 根据用户名查询用户信息，若果不存在就抛出异常
        //if (!username.equals("user")) {
        //    throw new RuntimeException("用户名不存在");
        //}
        // 必须进行加密，不然会报错,正常情况下，应该在注册的时候就进行加密转换,每次加密的盐=不一样
        String encode = passwordEncoder.encode("123456");
        log.info("数据库密码是：" + encode);
        // 数据库中用户和密码，以及相应的权限信息，密码会与前端传入的进行匹配,这是 Spring 提供的 UserDetails 实现类
        // 四个布尔分别对应 UserDetails 实现接口对象的状态信息，用户校验逻辑
        return new SocialUser(username, encode, true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
