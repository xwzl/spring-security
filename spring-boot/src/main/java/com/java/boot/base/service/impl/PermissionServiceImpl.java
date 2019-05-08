package com.java.boot.base.service.impl;

import com.java.boot.base.model.Permission;
import com.java.boot.base.model.User;
import com.java.boot.base.mapper.PermissionMapper;
import com.java.boot.base.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwz
 * @since 2019-04-29
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionByUser(User user) {
        return permissionMapper.findPermissionByUser(user);
    }

}
