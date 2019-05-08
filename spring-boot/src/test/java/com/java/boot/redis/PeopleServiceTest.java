package com.java.boot.redis;

import com.java.boot.SpringBootsTest;
import com.java.boot.base.model.People;
import com.java.boot.base.service.PeopleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xuweizhi
 * @date 2019/04/23 16:28
 */
public class PeopleServiceTest extends SpringBootsTest {

    @Autowired
    private PeopleService peopleService;

    @Test
    public void getUser() {
        People byId = peopleService.findById(5);
        System.out.println(byId);
    }
}
