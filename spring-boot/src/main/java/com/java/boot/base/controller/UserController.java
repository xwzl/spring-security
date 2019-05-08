package com.java.boot.base.controller;


import com.java.boot.base.model.People;
import com.java.boot.system.annotation.ControllerStatistics;
import com.java.boot.system.annotation.DataSource;
import com.java.boot.base.service.PeopleService;
import com.java.boot.base.until.redis.RedisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xwz
 * @since 2019-04-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final PeopleService peopleService;

    /**
     * 使用实现类 Redis 进行redis 操作
     */
    private final RedisService redisService;

    public UserController(PeopleService peopleService, RedisService redisService) {
        this.peopleService = peopleService;
        this.redisService = redisService;
    }

    /**
     * http://localhost:8083/spring-boot/swagger-ui.html#/ 访问
     */
    @ApiOperation(value = "Slave1获取", notes = "hello接口")
    @ApiParam(value = "api 测试")
    @GetMapping("/getUsers")
    @DataSource("slave1")
    public List<People> getUsers(String api) {
        System.out.println(api);
        People user = new People();
        user.setUId(3);
        People service = peopleService.getById(user);
        if (service != null) {
            redisService.setBean(service.getUId() + service.getAddress(), service);
            People redisUser = (People) redisService.getBean(service.getUId() + service.getAddress());
            System.out.println(redisUser);
        }
        return peopleService.getALl();
    }

    @ApiOperation(value = "Master 插入值", notes = "hello接口")
    @GetMapping("/insertUser")
    @DataSource
    @ControllerStatistics
    public People insertUser() {
        People user = new People(null, "山东", "仁和春天", LocalDateTime.now(), "158262751", "158262751", 2, "王柳");
        return peopleService.insert(user);
    }


    @ApiOperation(value = "update", notes = "更新")
    @GetMapping("/update")
    public People update(String name, Integer id) {
        People byId = peopleService.getById(id);
        byId.setUsername(name);
        return peopleService.update(byId);
    }

    @ApiOperation(value = "Slave 2", notes = "获取")
    @GetMapping("/getUser")
    @DataSource("slave2")
    public People getUser(Integer id) {
        return peopleService.findById(id);
    }

    @ApiOperation(value = "slave2", notes = "hello接口")
    @GetMapping("/delete")
    public void delete(Integer id) {
        People user = new People();
        user.setUId(id);
        peopleService.delete(user);
    }

    @ApiOperation(value = "slave1", notes = "hello接口")
    @GetMapping("/getPlus")
    public People getPlus(Integer id) {
        return peopleService.getById(id);
    }
}
