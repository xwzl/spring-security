package com.security.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.security.validated.MyConstraint;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 用来存放restful风格的api
 *
 * @author xuweizhi
 * @since 2019/05/11 21:31
 */
@Component
public class User {

    /**
     * 使用接口声明多个视图
     * <p>
     * 声明接口，用来指定特定的视图，需要@JsonView和对应@Controller的维护
     */
    public interface UserSimpleView {
    }

    /**
     * 继承接口,在特定的视图进行数据展示，默认会展示父类的数据
     */
    public interface UserDetailView extends UserSimpleView {

    }

    private String id;

    @MyConstraint(message = "自定义校验器,监测注解类型")
    private String username;

    /**
     * 数据校验，不为空
     */
    @NotBlank(message="密码不能为空")
    private String password;

    /**
     * 生日必须是过去的时间
     */
    @Past(message = "日期必须是过去的时间")
    private Date birthday;

    /**
     * 在值对象的get方法上指定视图，指定仅在 UserSimpleView 视图上显示数据
     */
    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 在值对象的get方法上指定视图，指定 UserDetailView 视图显示数据
     */
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
