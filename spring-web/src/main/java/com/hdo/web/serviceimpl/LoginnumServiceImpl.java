package com.hdo.web.serviceimpl;

import com.hdo.web.mapper.LoginnumMapper;
import com.hdo.web.model.Loginnum;
import com.hdo.web.service.LoginnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName LoginnumServiceImpl
 * @Author XWZ
 * @Description
 * @Date 2018-08-28 22:45 星期二 SpringBootProgram
 * @VERSION 1.0.0
 **/
@Service
public class LoginnumServiceImpl implements LoginnumService {

    @Autowired
    private LoginnumMapper loginnumMapper;

    @Transactional
    @Override
    public Loginnum getLoginnumByid(Integer id) {
        return loginnumMapper.getLoginnumByid(id);
    }

    @Transactional
    @Override
    public int deleteLoginnumById(Integer id) {
        return loginnumMapper.deleteLoginnumById(id);
    }

    @Transactional
    @Override
    public int insertDerp(Loginnum loginnum) {
        return loginnumMapper.insertDerp(loginnum);
    }

    @Transactional
    @Override
    public int updateLoginnum(Loginnum loginnum) {
        return loginnumMapper.updateLoginnum(loginnum);
    }
}
