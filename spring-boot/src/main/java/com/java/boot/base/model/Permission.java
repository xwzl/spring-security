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
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限主键")
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    @ApiModelProperty("权限秒速")
    private String description;

    @ApiModelProperty("用户密码")
    private String keyword;

    @ApiModelProperty("用户姓名")
    private String name;


}
