package com.security.demo.controller.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单处理结果
 *
 * @author xuweizhi
 * @since 2019/05/12 16:00
 */
@Component
public class DeferredResultHolder {

    /**
     * key: orderId
     * value: 处理结果
     */
    Map<String, DeferredResult<String>> map = new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
    
}
