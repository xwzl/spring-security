package com.java.tool.swagger;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuweizhi
 * @date 2019/03/26 16:23
 */
@Component
@Data
public class People {

    @Value("${people.username}")
    private String username;

    /**
     * 确实值的情况下，设置默认值
     */
    @Value("${people.ha:'我们'}")
    private String ha;

    /**
     * el内部字符串使用String的方法
     */
    @Value("#{'${people.list}'.split(',')}")
    private List<String> list;

    /**
     * 注意：这里只生成一次，之后你无论调用多少次getRandomValue()，都返回同一个值
     */
    @Value("#{T(java.lang.Math).random() * 10}")
    private String randomValue;


}
