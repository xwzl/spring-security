package com.java.boot.redis;

import com.java.boot.SpringBootsTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;

/**
 * @author xuweizhi
 * @date 2018/11/14 13:04
 */
public class HashRedisTest extends SpringBootsTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HashOperations opsForHash;

    @Test
    public void testPut() {
        opsForHash.put("he1", "key1", "a");
        opsForHash.put("he1", "key2", "b");
        opsForHash.put("he1", "key3", "c");
        Map<String, Object> entries = opsForHash.entries("he1");
        //{key3=c, key1=a, key2=b}(无序)
        System.out.println(entries);
    }


    public void testPutAll() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key1", "a");
        param.put("key2", "b");
        param.put("key3", "c");
        opsForHash.putAll("he2", param);
        //{key2=b, key1=a, key3=c}
        System.out.println(opsForHash.entries("he2"));
    }

    public void testDelete() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key1", "a");
        param.put("key2", "b");
        param.put("key3", "c");
        opsForHash.putAll("he3", param);
        //{key3=c, key2=b, key1=a}
        System.out.println(opsForHash.entries("he3"));
        opsForHash.delete("he3", "key1");
        //{key2=b, key3=c}
        System.out.println(opsForHash.entries("he3"));
    }

    public void testHashKey() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key1", "a");
        param.put("key2", "b");
        param.put("key3", "c");
        opsForHash.putAll("he4", param);
        System.out.println(opsForHash.hasKey("he", "key2"));//false
        System.out.println(opsForHash.hasKey("he4", "key4"));//false
        System.out.println(opsForHash.hasKey("he4", "key2"));//true
    }

    public void testGet() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key1", "a");
        param.put("key2", "b");
        param.put("key3", "c");
        opsForHash.putAll("he5", param);
        System.out.println(opsForHash.get("he5", "key1"));//a
        System.out.println(opsForHash.get("he5", "key"));//null
    }

    public void testMultiGet() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key1", "a");
        param.put("key2", "b");
        param.put("key3", "c");
        opsForHash.putAll("he6", param);
        List<String> keys = new ArrayList<String>();
        keys.add("key1");
        keys.add("key");
        keys.add("key2");
        System.out.println(opsForHash.multiGet("he6", keys));//[a, null, b]
    }

    public void testIncrement() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key1", "a");
        param.put("key2", "b");
        param.put("key3", "c");
        param.put("key4", 4);
        opsForHash.putAll("he7", param);
        System.out.println(opsForHash.increment("he7", "key4", 1));//5
        System.out.println(opsForHash.increment("he7", "key4", 1.1));//6.1
        try {
            opsForHash.increment("he7", "key1", 1);//ERR hash value is not an integer
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            opsForHash.increment("he7", "key1", 1.1);//ERR hash value is not a float
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testKeys() {
        redisTemplate.delete("he8");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key4", "d");
        param.put("key1", "a");
        param.put("key3", "c");
        param.put("key5", "e");
        param.put("key2", "b");
        opsForHash.putAll("he8", param);
        Set<String> keys = opsForHash.keys("he8");
        System.out.println(keys);//[key4, key3, key5, key2, key1]
    }

    public void testSize() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key4", "d");
        param.put("key1", "a");
        param.put("key3", "c");
        param.put("key5", "e");
        param.put("key2", "b");
        opsForHash.putAll("he9", param);
        System.out.println(opsForHash.size("he9"));//5
    }

    public void testPutIfAbsent() {
        //仅当hashKey不存在时才设置散列hashKey的值。
        System.out.println(opsForHash.putIfAbsent("he10", "key1", "a"));//true
        System.out.println(opsForHash.putIfAbsent("he10", "key1", "a"));//false
    }

    public void testValues() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key4", "d");
        param.put("key1", "a");
        param.put("key3", "c");
        param.put("key5", "e");
        param.put("key2", "b");
        opsForHash.putAll("he11", param);
        List<Object> values = opsForHash.values("he11");
        System.out.println(values);//[d, c, e, b, a]
    }

    public void testScan() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("key4", "d");
        param.put("key1", "a");
        param.put("key3", "c");
        param.put("key5", "e");
        param.put("key2", "b");
        opsForHash.putAll("he13", param);
        Cursor<Map.Entry<String, Object>> curosr = opsForHash.scan("he13", ScanOptions.NONE);
        while (curosr.hasNext()) {
            Map.Entry<String, Object> entry = curosr.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        /**
         key4:d
         key3:c
         key5:e
         key2:b
         key1:a
         */
    }


}