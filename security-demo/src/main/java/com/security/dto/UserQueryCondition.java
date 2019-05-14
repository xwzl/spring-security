package com.security.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xuweizhi
 * @since 2019/05/11 22:59
 */
public class UserQueryCondition {

    /**
     * 指定字段信息
     */
    @ApiModelProperty(value="用户名")
    private String username;

    private int age;

    private int ageTo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }
}
