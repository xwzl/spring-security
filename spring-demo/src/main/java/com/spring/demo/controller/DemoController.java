package com.spring.demo.controller;


import com.spring.demo.model.Demo;
import com.spring.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuweizhi
 * @since 2019-06-28
 */
@RestController
@Slf4j
@RequestMapping("/demo/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;
    @PostMapping
    public Demo addDemo(Demo demo) {
        demoService.save(demo);
        log.info(demo.toString());
        return demo;
    }

    @GetMapping
    public Demo getDemo(String id) {
        return demoService.getById(id);
    }



}
