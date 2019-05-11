package com.java.plus.old;

/**
 * mybatis 辅助类
 *
 * @author xuweizhi
 * @date 2019/04/03 18:10
 */
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

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getChildModuleName() {
        return childModuleName;
    }

    public void setChildModuleName(String childModuleName) {
        this.childModuleName = childModuleName;
    }

    public boolean isChildModule() {
        return isChildModule;
    }

    public void setChildModule(boolean childModule) {
        isChildModule = childModule;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBaseControllerSimpleName() {
        return baseControllerSimpleName;
    }

    public void setBaseControllerSimpleName(String baseControllerSimpleName) {
        this.baseControllerSimpleName = baseControllerSimpleName;
    }

    public String getBaseServiceSimpleName() {
        return baseServiceSimpleName;
    }

    public void setBaseServiceSimpleName(String baseServiceSimpleName) {
        this.baseServiceSimpleName = baseServiceSimpleName;
    }

    public String getBaseServiceImplSimpleName() {
        return baseServiceImplSimpleName;
    }

    public void setBaseServiceImplSimpleName(String baseServiceImplSimpleName) {
        this.baseServiceImplSimpleName = baseServiceImplSimpleName;
    }

    public String getBaseEntitySimpleName() {
        return baseEntitySimpleName;
    }

    public void setBaseEntitySimpleName(String baseEntitySimpleName) {
        this.baseEntitySimpleName = baseEntitySimpleName;
    }
}
