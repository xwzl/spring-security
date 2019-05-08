package com.java.boot.base.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * Document注解里面的几个属性，类比mysql的话是这样：
 *    index –> DB
 *    type –> Table
 *    Document –> row
 *
 * Id注解加上后，在Elasticsearch里相应于该列就是主键了，在查询时就可以直接用主键查询，后面再看。
 * 其实和mysql非常类似，基本就是一个数据库。
 *
 * @author xuweizhi
 */
@Document(indexName = "project",type = "article",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class Article implements Serializable {

    private static final long serialVersionUID = -1430273295535121790L;

    @Id
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 内容
     */
    private String content;

    /**
     * 发表时间
     */
    private String postTime;

    /**
     * 点击率
     */
    private String clickCount;

    /**
     * 作者
     */
    private Author author;

    /**
     * 所属教程
     */
    private Tutorial tutorial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }
}