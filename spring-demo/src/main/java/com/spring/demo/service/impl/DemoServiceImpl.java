package com.spring.demo.service.impl;

import com.spring.demo.model.Demo;
import com.spring.demo.mapper.DemoMapper;
import com.spring.demo.service.DemoService;
import com.spring.demo.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuweizhi
 * @since 2019-06-28
 */
@Service
public class DemoServiceImpl extends BaseServiceImpl<DemoMapper, Demo> implements DemoService {

}
