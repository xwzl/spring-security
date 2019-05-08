package com.java.boot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xuweizhi
 * @date 2018/11/14 13:16
 */
@Service
public class StringRedisTest {

    @Autowired
    private RedisTemplate<String, Object> stringTemplate;

    @Autowired
    private ValueOperations<String, String> opsForValue;

    public void testSet() {
        //删除健（每次测试前我都会对当前测试的键进行删除，防止影响测试结果）
        stringTemplate.delete("liu1");
        opsForValue.set("liu1", "liu1");
        System.out.println(opsForValue.get("liu1"));
    }

    public void testSetTimeOut() throws InterruptedException {
        stringTemplate.delete("liu2");
        //加了失效机制,超时10ms获取不到值
        opsForValue.set("liu2", "liu2", 10, TimeUnit.SECONDS);
        Thread.sleep(5000);
        System.out.println(opsForValue.get("liu2"));//liu2
        Thread.sleep(5000);
        System.out.println(opsForValue.get("liu2"));//null
    }

    public void testSetOverwrite() {
        stringTemplate.delete("liu3");
        opsForValue.set("liu3", "liu3");
        System.out.println(opsForValue.get("liu3"));//liu3
        //该方法是用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
        opsForValue.set("liu3", "666666", 1);
        System.out.println(opsForValue.get("liu3"));//l666666
    }

    public void testSetIfAbsent() {
        stringTemplate.delete("liu4");
        stringTemplate.delete("liu5");
        opsForValue.set("liu4", "liu4");
        System.out.println(opsForValue.setIfAbsent("liu4", "liu4"));//false
        System.out.println(opsForValue.setIfAbsent("liu5", "liu5"));//true
    }

    public void testMultiSetAndGet() {
        stringTemplate.delete("liu6");
        stringTemplate.delete("liu7");
        stringTemplate.delete("liu8");
        stringTemplate.delete("liu9");
        Map<String, String> param = new HashMap<String, String>();
        param.put("liu6", "liu6");
        param.put("liu7", "liu7");
        param.put("liu8", "liu8");
        //为多个键分别设置它们的值
        opsForValue.multiSet(param);
        List<String> keys = new ArrayList<String>();
        keys.add("liu6");
        keys.add("liu7");
        keys.add("liu8");
        //为多个键分别取出它们的值
        List<String> results = opsForValue.multiGet(keys);
        for (String result : results) {
            System.out.println(result);
            /*
                liu6
                liu7
                liu8
             */
        }
        param.clear();
        param.put("liu8", "hahaha");
        param.put("liu9", "liu9");
        //为多个键分别设置它们的值，如果存在则返回false，不存在返回true
        System.out.println(opsForValue.multiSetIfAbsent(param));//false
        System.out.println(opsForValue.get("liu8"));//liu8
    }

    public void testGetAndSet() {
        stringTemplate.delete("liu9");
        opsForValue.set("liu9", "liu9");
        //设置键的字符串值并返回其旧值
        System.out.println(opsForValue.getAndSet("liu9", "haha"));//liu9
        System.out.println(opsForValue.get("liu9"));//haha
    }

    public void testIncrement() {
        stringTemplate.delete("liu10");
        opsForValue.set("liu10", "6");
        //值增长，支持整形和浮点型
        System.out.println(opsForValue.increment("liu10", 1));//7
        System.out.println(opsForValue.increment("liu10", 1.1));//8.1
        opsForValue.set("liu10", "liu10");
        opsForValue.increment("liu10", 1);//redis.clients.jedis.exceptions.JedisDataException: ERR value is not an integer or out of range
    }

    public void testAppend() {
        stringTemplate.delete("liu11");
        stringTemplate.delete("liu12");
        //如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
        opsForValue.append("liu11", "liu11");
        System.out.println(opsForValue.get("liu11"));//liu11
        opsForValue.set("liu12", "liu12");
        opsForValue.append("liu12", "haha");
        System.out.println(opsForValue.get("liu12"));//liu12haha
    }

    public void testSize() {
        stringTemplate.delete("liu14");
        opsForValue.set("liu14", "liu14");
        //返回key所对应的value值得长度
        System.out.println(opsForValue.size("liu14"));//5
    }

    public void testSetBit() {
        stringTemplate.delete("liu15");
        //true为1，false为0
        opsForValue.set("liu15", "liu15");
        //对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
        //key键对应的值value对应的ASCII码,在offset的位置(从左向右数)变为value
        System.out.println(opsForValue.setBit("liu15", 13, true));//false
        System.out.println(opsForValue.get("liu15"));//lmu15
        for (int i = 0; i < "liu15".length() * 8; i++) {
            if (opsForValue.getBit("liu15", i)) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
            //0110110001101101011101010011000100110101
        }
    }

}
