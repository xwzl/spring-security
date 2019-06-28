package com.spring.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author xuweizhi
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("demo")
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("date")
    private LocalDateTime date;

    @TableField("time")
    private String time;

    @TableField("sf")
    private String sf;

    public Demo(Integer id, LocalDateTime date, String time, String sf) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.sf = sf;
    }
}
