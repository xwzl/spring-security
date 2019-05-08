package com.java.boot.base.model;

/**
 * @author xuweizhi
 * @since  2019/04/22 14:38
 */
public class Goods {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                '}';
    }
}
