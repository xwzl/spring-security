package com.security.controller.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 队列的监听器
 * <p>
 * ContextRefreshedEvent Spring 容器初始化完毕的事件
 *
 * @author xuweizhi
 * @since 2019/05/12 16:08
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * 监听容器启动完毕后的需要完成的任务,主线程启动完成启动，所以另起线程执行任务，不然一直循环
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        new Thread(() -> {
            while (true) {
                // 当虚拟的消息队列有值得的时候
                if (StringUtils.isNoneBlank(mockQueue.getCompleteOrder())) {

                    String completeOrder = mockQueue.getCompleteOrder();

                    log.info("订单返回的处理结果:" + completeOrder);
                    // 返回处理结果
                    deferredResultHolder.getMap().get(completeOrder).setResult("Place order success !");

                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
