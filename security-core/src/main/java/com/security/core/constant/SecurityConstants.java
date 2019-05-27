package com.security.core.constant;

/**
 * URL常量信息
 *
 * @author xuweizhi
 * @date 2019/05/15 19:05
 */
public interface SecurityConstants {

    /**
     * 登录地址
     */
    public final static String LOGIN_URL = "/authentication/form";

    /**
     * 认证拦截地址
     */
    public final static String AUTHENTICATION_URL = "/authentication/require";

    /**
     * 静态地址
     */
    public final static String STATIC_RESOURCES_URL = "/static/**";


    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 当请求需要身份认证时，默认跳转的url,各自模块的处理层实现
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认登录页面
     */
    String DEFAULT_LOGIN_PAGE_URL = "/login.html";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL = "/session/invalid.html";

    /**
     * openid参数名
     */
    String DEFAULT_PARAMETER_NAME_OPENID = "openId";

    /**
     * providerId参数名
     */
    String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";

    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/authentication/openid";

    /**
     * 获取第三方用户信息的url
     */
    String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";


}
