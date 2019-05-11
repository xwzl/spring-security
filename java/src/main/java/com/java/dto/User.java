package com.java.dto;

/**
 * 用来存放restful风格的api
 *
 * @author xuweizhi
 * @since 2019/05/11 21:31
 */
public class User {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
