package com.java.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author xuweizhi
 * @since 2019/05/11 21:14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySource() throws Exception {
        // perform 执行
        mockMvc.perform(MockMvcRequestBuilders.get("/test")
                .param("username","job")
                .param("age","1")
                //.param("page","12")
                //.param("size","13")
                //.param("sort","age,desc")
                // 编码格式
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // 期望结果
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 期望返回的结果为集合，且长度为3
                // https://github.com/json-path/JsonPath 查看文档
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

}
