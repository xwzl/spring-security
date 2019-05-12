package com.security.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySource() throws Exception {
        // perform 执行
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username", "job")
                .param("age", "1")
                //.param("page","12")
                //.param("size","13")
                //.param("sort","age,desc")
                // 编码格式
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // 期望结果
                .andExpect(status().isOk())
                // 期望返回的结果为集合，且长度为3
                // https://github.com/json-path/JsonPath 查看文档
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * 返回匹配的值
     */
    @Test
    public void whenGetInfoSuccess() throws Exception {
        String tom = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                // jsonPath 的值，理解为json格式即可
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(tom);
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
        //.andExpect(MockMvcResultMatchers.jsonPath("$"));
    }

    /**
     * 前后端分离，传时间戳
     */
    @Test
    public void whenCreateSuccess() throws Exception {
        Date date = new Date();
        System.out.println(date.getTime());
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String content1 = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                // 请求内容
                .content(content))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(content1);
    }

    /**
     * 处理修改请求
     */
    @Test
    public void whenUpdateSuccess() throws Exception {
        // 一年以后的时间
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli());
        System.out.println(date.getTime());
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String result = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @SuppressWarnings("deprecated")
    public void whenUploadSuccess() throws Exception {
        String content = mockMvc.perform(fileUpload("/file")
                // 模拟文件上传，参数是file,文件名是test.txt
                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello file".getBytes())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }
}
