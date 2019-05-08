package com.java.boot.base.service.impl;

import com.java.boot.base.model.Role;
import com.java.boot.base.model.User;
import com.java.boot.base.mapper.RoleMapper;
import com.java.boot.base.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xwz
 * @since 2019-04-29
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> findRolesByUser(User user) {
        return roleMapper.findRolesByUser(user);
    }
}
