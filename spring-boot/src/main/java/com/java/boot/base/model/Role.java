package com.java.boot.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xwz
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色主键")
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    @ApiModelProperty("角色秒速")
    private String description;

    @ApiModelProperty("密码")
    private String keyword;

    @ApiModelProperty("姓名")
    private String name;


}
