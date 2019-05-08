package com.java.boot.base.entity;

import org.jetbrains.annotations.Contract;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * indexName索引名称 可以理解为数据库名 必须为小写 不然会报 org.elasticsearch.indices.InvalidIndexNameException异常
 * type类型 可以理解为表名
 *
 * @author xuweizhi
 * @date 2019/04/25 15:38
 */
@Document(indexName = "wtf", type = "goods")
public class GoodsInfo implements Serializable {

    private static final long serialVersionUID = -2044038806144087644L;

    private Long id;

    private String name;

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Contract(pure = true)
    public GoodsInfo(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Contract(pure = true)
    public GoodsInfo() {

    }

}