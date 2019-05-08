package com.java.boot.base.mapper;

import com.java.boot.base.model.Permission;
import com.java.boot.base.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xwz
 * @since 2019-04-29
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissionByUser(User user);
}
