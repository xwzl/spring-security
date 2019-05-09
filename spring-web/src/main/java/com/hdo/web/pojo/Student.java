package com.hdo.web.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Student
 * @Author XWZ
 * @Description
 * @Date 2018-08-22 17:17 星期三 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
public class Student implements Serializable {

    private String name;

    private String pass;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", date=" + date +
                '}';
    }
}
