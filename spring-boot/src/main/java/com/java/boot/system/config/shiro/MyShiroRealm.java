package com.java.boot.system.config.shiro;

/**
 * 自定义权限匹配和账号密码匹配
 */
public class MyShiroRealm /*extends AuthorizingRealm */{
    /* *//**
     * 描述：userService对象
     *//*
    @Autowired
    private UserService userService;

    *//**
     * 描述：roleService对象
     *//*
    @Autowired
    private RoleService roleService;

    *//**
     * 描述：permissionService对象
     *//*
    @Autowired
    private PermissionService permissionService;

    *//**
     * shiro授权
     *//*
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //创建授权对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取已经认证通过的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //根据用户信息查找对应的角色
        List<Role> roleList = roleService.findRolesByUser(user);
        for (Role role : roleList) {
            authorizationInfo.addRole(role.getKeyword());
        }
        //根据用户信息查找对应的权限
        List<Permission> permissionList = permissionService.findPermissionByUser(user);
        for (Permission permission : permissionList) {
            authorizationInfo.addStringPermission(permission.getKeyword());
        }
        return authorizationInfo;
    }

    *//**
     * shiro认证
     * <p>
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     *//*
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户输入的用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //获取用户名
        String username = upToken.getUsername();
        //根据用户名去数据库查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userService.getOne(queryWrapper);
        //用户名不存在
        if (user == null) {
            return null;
        }
        //用户名存在，进去密码比较器比较密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
    }*/

}
