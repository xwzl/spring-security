package com.java.boot.base.entity;

import java.io.Serializable;

/**
 * @author xuweizhi
 * @since  2019-5-8 20:12
 */
public class Author  implements Serializable {

    private static final long serialVersionUID = 7062191789697170927L;

    /**
     * 作者ID
     */
    private Long id;
    /**
     * 作者姓名
     */
    private String name;
    /**
     * 作者简介
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}