package com.java.boot.base.entity;

import java.io.Serializable;

/**
 * @author xuweizhi
 */
public class Tutorial implements Serializable {

    private static final long serialVersionUID = -638540580193126565L;

    private Long id;

    /**
     * 教程名称
     */
    private String name;

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
}