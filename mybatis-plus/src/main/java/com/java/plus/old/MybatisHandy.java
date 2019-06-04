package com.java.plus.old;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

/**
 * mybatis 辅助类
 *
 * @author xuweizhi
 * @date 2019/04/03 18:10
 */
@Data
public class MybatisHandy {

    /**
     * 模块类型前缀
     */
    private String basePackageName;

    /**
     * MySql URL
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 用户密码是
     */
    private String password;

    /**
     * sql驱动全路径
     */
    private String classPath;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 子模块名称
     */
    private String childModuleName;

    /**
     * 判断是不是子模块，默认为false
     */
    private boolean isChildModule = false;

    /**
     * 作者
     */
    private String author;

    /**
     * 基类controller名称
     */
    private String baseControllerSimpleName;

    /**
     * 基类服务名称
     */
    private String baseServiceSimpleName;

    /**
     * 基类服务实现名称
     */
    private String baseServiceImplSimpleName;

    /**
     * 基类Entity简单名称
     */
    private String baseEntitySimpleName;

    /**
     * 是否生成字段名常量
     */
    private boolean entityColumnConstant = false;

    /**
     * 基础的列表值
     */
    private boolean baseColumnList = false;

    /**
     * 基础的返回结果
     */
    private boolean baseResultMap = false;

    /**
     * 主键策略
     */
    private IdType idType;


}
