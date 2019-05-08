package com.java.boot.base.model;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 角色表
    * </p>
*
* @author xwz
* @since 2019-04-29
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value="SysRole对象", description="角色表")
    public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "编号")
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            @ApiModelProperty(value = "角色名称")
    private String name;

            @ApiModelProperty(value = "英文名称")
    private String enname;

            @ApiModelProperty(value = "数据范围")
    private String dataScope;

            @ApiModelProperty(value = "角色类型")
    private Integer roleType;

            @ApiModelProperty(value = "是否系统数据")
    private String isSys;

            @ApiModelProperty(value = "是否可用")
    private Integer useable;

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
