package com.hdo.web.service;

import com.hdo.web.model.Loginnum;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName LoginnumService
 * @Author XWZ
 * @Description
 * @Date 2018-08-28 22:44 星期二 SpringBootProgram
 * @VERSION 1.0.0
 **/
public interface LoginnumService {

    public Loginnum getLoginnumByid(Integer id);

    public int deleteLoginnumById(Integer id);

    public int insertDerp(Loginnum loginnum);

    public int updateLoginnum(Loginnum loginnum);
}
