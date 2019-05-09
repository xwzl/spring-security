package com.hdo.web.bean;

/**
 * @ClassName User
 * @Author XWZ
 * @Description
 * @Date 2018-08-23 18:31 星期四 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
