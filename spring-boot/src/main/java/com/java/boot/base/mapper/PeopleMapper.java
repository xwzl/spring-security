package com.java.boot.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.boot.base.model.People;
import com.java.boot.system.annotation.MapperStatistics;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xwz
 * @since 2019-04-22
 */
public interface PeopleMapper extends BaseMapper<People> {

    /**
     * 获取用户信息
     *
     * @param uId 主键
     * @return 获取id
     */
    @Select("select * from people where u_id=#{uId}")
    People getUser(Integer uId);

    /**
     * 新增用户信息
     *
     * @param user 用户
     * @return 是否新增成功
     */
    @Insert("insert into people(address,role) values(#{address},#{role})")
    @Options(keyProperty = "uId", useGeneratedKeys = true, keyColumn = "u_id")
    @MapperStatistics
    int addUser(People user);

    /**
     * 更新用户
     *
     * @param user 用户
     */
    @Update("update people set address=#{address} , role =#{role} where u_id=#{uId}")
    void updateUser(People user);

    /**
     * 获取所有用户信息
     *
     * @return 用户
     */
    @Select("select * from people")
    List<People> getALl();

    /**
     * 删除
     *
     * @param user 删除单个用户
     */
    @Delete("delete from people where u_id=#{uId}")
    void deleteUser(People user);

    /**
     * XML 添加用户信息
     *
     * @param user 用户
     * @return 插入是否成功
     */
    int addUserByXml(People user);

    /**
     * 获取用户信息
     *
     * @param uId id
     * @return user
     */
    People getUserByIdXml(Integer uId);

    /**
     * 获取所有用户信息
     *
     * @return ha
     */
    List<People> getAllByXml();

    /**
     * 插入 user
     *
     * @param uesr 用户
     * @return 返回值
     */
    int insertUserOtherTag(People uesr);

    /**
     * 更新用户
     *
     * @param uer 用户
     */
    void updateUserOtherTag(People uer);

    /**
     * Mybatis
     *
     * @param map 入参
     * @return 返回值
     */
    List<People> getUserByMap(Map<String, Object> map);

    /**
     * 哈哈
     *
     * @param address 地址
     * @param role    角色
     * @return 返回值
     */
    List<People> getUserByParam(@Param("address") String address, @Param("role") Integer role);
}
