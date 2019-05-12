package com.security.demo.controller.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * 异步请求
 *
 * @author xuweizhi
 * @since 2019/05/12 15:39
 */
@RestController
public class AsyncController {

    private final Logger log = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * 模拟消息队列，主线程产生消息，设置 orderNumber 到 MockQueue 的属性 completeOrder，
     * 并设置结果到 deferredResultHolder中，有线程 2 处理返回结果。
     */
    @RequestMapping("/loaderDeferredResult")
    public DeferredResult<String> loader() throws InterruptedException {
        log.info("主线程开始");

        String orderNumber = RandomStringUtils.randomNumeric(8);
        // 注入消息队列
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

        log.info("主线程结束");
        return result;
    }


    /**
     * 异步执行
     */
    @RequestMapping("/loaderCallable")
    public Callable<String> loaderCallable() throws InterruptedException {
        log.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("副线程开始");
                Thread.sleep(1000);
                log.info("副线程结束");
                return "success";
            }
        };
        log.info("主线程结束");
        return result;
    }

}
