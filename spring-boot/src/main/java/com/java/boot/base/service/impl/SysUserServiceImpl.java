package com.java.boot.base.service.impl;

import com.java.boot.base.model.SysUser;
import com.java.boot.base.mapper.SysUserMapper;
import com.java.boot.base.service.SysUserService;
import com.java.boot.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xwz
 * @since 2019-04-29
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
