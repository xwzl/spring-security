package com.hdo.web.model;

import java.util.Date;

public class Loginnum {
    private Integer id;

    private String department;

    private String ipAddress;

    private Date loginTime;

    private String name;

    private String number;

    private Integer totalLoginNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Integer getTotalLoginNum() {
        return totalLoginNum;
    }

    public void setTotalLoginNum(Integer totalLoginNum) {
        this.totalLoginNum = totalLoginNum;
    }

    @Override
    public String toString() {
        return "Loginnum{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", loginTime=" + loginTime +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", totalLoginNum=" + totalLoginNum +
                '}';
    }
}