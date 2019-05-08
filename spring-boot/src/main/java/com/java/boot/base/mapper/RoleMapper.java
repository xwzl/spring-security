package com.java.boot.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.boot.base.model.Role;
import com.java.boot.base.model.User;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xwz
 * @since 2019-04-29
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过 User 对象 uid 获取 Role List 列表
     *
     * @param user 必须有参数
     * @return 返回 role 集合
     */
    List<Role> findRolesByUser(User user);

}
