package com.java.boot.base.service.impl;

import com.java.boot.base.model.User;
import com.java.boot.base.mapper.UserMapper;
import com.java.boot.base.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwz
 * @since 2019-04-29
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

}
