package com.hdo.web.mapper;

import com.hdo.web.model.Loginnum;
import org.apache.ibatis.annotations.*;

//指定这是一个操作数据库的Mapper
@Mapper
public interface LoginnumMapper {

    @Select("select * from loginnum where id =#{id}")
    public Loginnum  getLoginnumByid(Integer id);

    @Delete("delete from loginnum where id = #{id}")
    public int deleteLoginnumById(Integer id);

    @Insert("insert into loginnum(department) values(#{department})")
    public int insertDerp(Loginnum loginnum);

    @Update("update loginnum set department=#{department} where id = #{id}")
    public int updateLoginnum(Loginnum loginnum);

   /* int deleteByPrimaryKey(Integer id);

    int insert(Loginnum record);

    int insertSelective(Loginnum record);

    Loginnum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Loginnum record);

    int updateByPrimaryKey(Loginnum record);*/

}
