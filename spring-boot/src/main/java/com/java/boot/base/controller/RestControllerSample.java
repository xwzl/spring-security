package com.java.boot.base.controller;

import com.java.boot.system.config.date.DateConfig;
import com.java.boot.system.config.date.LocalDateTimeSerializerConfig;
import com.java.boot.system.property.ReadProperties;
import com.java.boot.base.model.Book;
import com.java.boot.base.model.Goods;
import com.java.boot.base.model.ValueSample;
import com.java.boot.base.until.LogUtils;
import io.swagger.annotations.Api;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author xuweizhi
 * @date 2019/04/22 14:10
 */
@RestController
@RequestMapping("/rest")
@Api
public class RestControllerSample {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerSample.class);

    /**
     * 条件注入判断
     */
    private final Book book;

    /**
     * 条件注入判断
     */
    private final Goods goods;

    /**
     * 配置文件注入
     */
    private final ValueSample valueSample;

    /**
     * 读取配置文件中注入Redis 类中的信息
     */
    private final ReadProperties readProperties;

    @Contract(pure = true)
    public RestControllerSample(Book book, Goods goods, ValueSample valueSample, ReadProperties readProperties) {
        this.book = book;
        this.goods = goods;
        this.valueSample = valueSample;
        this.readProperties = readProperties;
    }

    /**
     * http://localhost:8083/spring-boot/swagger-ui.html#/ 访问
     */
    @GetMapping("/condition")
    public Book getBook() {
        logger.info("我们都是好孩子!");
        return book;
    }


    @GetMapping("/goods")
    public Goods getGoods() {
        return goods;
    }

    @GetMapping("/value")
    public ValueSample getValue() {
        return valueSample;
    }


    @GetMapping("/redis")
    public void getRedis() {
        readProperties.read();
    }

    /**
     * 日志打印，必须配合 log4j2.yml 使用
     */
    @GetMapping("/log")
    public String log() throws Exception {
        Logger log = LogUtils.getExceptionLogger();
        Logger log1 = LogUtils.getBusinessLogger();
        Logger log2 = LogUtils.getDBLogger();
        log.error("getExceptionLogger===日志测试");
        log1.info("getBussinessLogger===日志测试");
        log2.debug("getDBLogger===日志测试");
        log2.info("1111111");

        logger.trace("I am trace log.");
        logger.debug("I am debug log.");
        logger.warn("I am warn log.");
        logger.error("I am error log.");

        return "helloworld";
    }

    /**
     * 处理 LocalDateTime 类型
     * {@link LocalDateTimeSerializerConfig} 好像转换并没有效果
     */
    @GetMapping("date")
    public Object date(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        return date.getClass();
    }

    /**
     * 处理 LocalDate 类型
     */
    @GetMapping("date2")
    public Object date(@NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return date.getClass();
    }

    /**
     * date :         2021-12-13
     * dateTime :     2021-12-13 12:12:11
     * originalDate : 2021-12-13 12:12:11
     * <p>
     * {@link DateConfig}
     */
    @GetMapping("/getDate")
    public LocalDateTime getDate(@RequestParam LocalDate date,
                                 @RequestParam LocalDateTime dateTime,
                                 @RequestParam Date originalDate) {
        System.out.println(date);
        System.out.println(dateTime);
        System.out.println(originalDate);
        return LocalDateTime.now();
    }
}
