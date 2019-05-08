package com.java.boot.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author xwz
 * @since 2019-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class People implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("人主键")
    @TableId(value = "u_id", type = IdType.AUTO)
    private Integer uId;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("公寓")
    private String apartment;

    /**
     * 无法解决Redis 序列化问题 @JsonFormat(pattern = "yyyy-MM-dd")
     */
    @ApiModelProperty("创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("电话号码")
    private String phoneNumber;

    @ApiModelProperty("角色")
    private Integer role;

    @ApiModelProperty("用户名")
    private String username;

    @Contract(pure = true)
    public People() {
    }

    @Contract(pure = true)
    public People(Integer uId, String address, String apartment, LocalDateTime createTime, String password, String phoneNumber, Integer role, String username) {
        this.uId = uId;
        this.address = address;
        this.apartment = apartment;
        this.createTime = createTime;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.username = username;
    }
}
