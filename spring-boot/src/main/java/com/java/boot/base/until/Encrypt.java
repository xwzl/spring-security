package com.java.boot.base.until;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Encrypt {

    /**
     * 功能描述：高强度加密算法,不可逆
     *
     * @param password:密码
     * @param salt：盐
     * @return java.lang.String
     * @since 2018/12/z 23:29
     **/
    public static String md5(String password, String salt) {
        return new Md5Hash(password, salt, 2).toString();
    }

}
