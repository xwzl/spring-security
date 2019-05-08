package com.java.boot.base.model;

    import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 用户表
    * </p>
*
* @author xwz
* @since 2019-04-29
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value="SysUser对象", description="用户表")
    public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "编号")
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            @ApiModelProperty(value = "登录名")
    private String loginName;

            @ApiModelProperty(value = "密码")
    private String password;

            @ApiModelProperty(value = "角色id")
    private Integer roleId;

            @ApiModelProperty(value = "工号")
    private String no;

            @ApiModelProperty(value = "姓名")
    private String name;

            @ApiModelProperty(value = "邮箱")
    private String email;

            @ApiModelProperty(value = "电话")
    private String phone;

            @ApiModelProperty(value = "手机")
    private String mobile;

            @ApiModelProperty(value = "用户头像")
    private String photo;

            @ApiModelProperty(value = "最后登陆IP")
    private String loginIp;

            @ApiModelProperty(value = "最后登陆时间")
    private LocalDateTime loginDate;

            @ApiModelProperty(value = "是否可登录")
    private String loginFlag;

            @ApiModelProperty(value = "创建者")
    private Integer createBy;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

            @ApiModelProperty(value = "更新者")
    private Integer updateBy;

            @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

            @ApiModelProperty(value = "备注信息")
    private String remarks;

            @ApiModelProperty(value = "删除标记")
    private String delFlag;


}
