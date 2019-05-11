package com.java.plus;

import com.java.plus.old.MybatisHandy;

/**
 * @author xuweizhi
 * @date 2019/04/03 19:13
 */
public class HandyTest {

    public static void main(String[] args) {
        MybatisHandy handy = new MybatisHandy();
        //如果是子模块，必须设置
        handy.setChildModule(true);
        handy.setChildModuleName("spring-boot");
        handy.setBasePackageName("com.java.boot");
        handy.setAuthor("xwz");
        handy.setUrl("192.168.26.22:3306/wtf");
       GeneratorUntil.generatorCode(handy);
    }
}
