package com.hdo.web.bean;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.sql.Date;

/**
 * @ClassName Test
 * @Author XWZ
 * @Description
 * @Date 2018-08-28 14:21 星期二 SpringBootProgram
 * @VERSION 1.0.0
 **/
public class Test extends BaseModel {

    private static final long serialVersionUID = 5199200306752426433L;

    private Integer	id;

    private String	name;

    private String	description;

    private Date create_time;

    private Date	update_time;

    private Long	number;

    private String	lifecycle;

    private Double	dekes;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getCreate_time(){
        return create_time;
    }

    public void setCreate_time(Date create_time){
        this.create_time = create_time;
    }

    public Date getUpdate_time(){
        return update_time;
    }

    public void setUpdate_time(Date update_time){
        this.update_time = update_time;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Long getNumber(){
        return number;
    }

    public void setNumber(Long number){
        this.number = number;
    }

    public String getLifecycle(){
        return lifecycle;
    }

    public void setLifecycle(String lifecycle){
        this.lifecycle = lifecycle;
    }

    public Double getDekes(){
        return dekes;
    }

    public void setDekes(Double dekes){
        this.dekes = dekes;
    }
}
