package com.java.boot.base.enums;

/**
 * 本地日志枚举
 *
 * @author Administrator
 */
public enum LogEnum {


    /**
     * business
     */
    BUSINESS("business"),
    /**
     * platform
     */
    PLATFORM("platform"),
    /**
     * db
     */
    DB("db"),
    /**
     * exception
     */
    EXCEPTION("exception");


    private String category;


    LogEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}