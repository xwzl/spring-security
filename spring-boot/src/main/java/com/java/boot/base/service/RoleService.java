package com.java.boot.base.service;

import com.java.boot.base.model.Role;
import com.java.boot.base.model.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xwz
 * @since 2019-04-29
 */
public interface RoleService extends BaseService<Role> {

    List<Role> findRolesByUser(User user);

}
