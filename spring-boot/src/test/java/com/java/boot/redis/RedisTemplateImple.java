package com.java.boot.redis;

import com.java.boot.base.model.People;
import com.java.boot.base.until.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuweizhi
 * @date 2018/11/14 9:41
 */
@Slf4j
public class RedisTemplateImple {

    @Autowired
    private RedisTemplate<String, Object> template;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisService redisService;


    public List<People> saveUserWithRedis(List<People> users) {

        ListOperations<String, Object> listOperations = template.opsForList();

        template.opsForValue().set("user", users.toString());

        //由于设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null
        template.opsForValue().set("jack", "tom", 10, TimeUnit.SECONDS);
        template.opsForValue().get("name");
        template.opsForList().rightPushAll("userlist", users);

        //获取集合长度
        Long userlist = listOperations.size("userlist");

        //0 -- size-1 获取集合数据
        List<Object> userlist1 = template.opsForList().range("userlist", 0, userlist - 1);
        log.info("UserList={}", template.opsForList().range("userlist", 0, -1));
        System.out.println(userlist1);
        return users;
    }

    //redis操作字符串
    public void redisString() {

        template.opsForValue().set("name", "tom");
        log.info("StirngOpsValue={}", template.opsForValue().get("name"));

        //redisTemplate.opsForValue().set("name","tom",10, TimeUnit.SECONDS);
        //由于设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null
        //redisTemplate.opsForValue().get("name");

        //set void set(K key, V value, long offset);

        //该方法是用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
        //multiSet void multiSet(Map<? extends K, ? extends V> m);
        //为多个键分别设置它们的值

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("multi1", "multi1");
        maps.put("multi2", "multi2");
        maps.put("multi3", "multi3");

        template.opsForValue().multiSet(maps);

        List<String> keys = new ArrayList<String>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");

        System.out.println(template.opsForValue().multiGet(keys));

        //getAndSet V getAndSet(K key, V value);
        //设置键的字符串值并返回其旧值

        template.opsForValue().set("getSetTest", "test");
        System.out.println(template.opsForValue().getAndSet("getSetTest", "test2"));

        //multiSetIfAbsent Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m);
        //为多个键分别设置它们的值，如果存在则返回false，不存在返回true

    }

    //Redis操作集合
    public void listRedis() {

        //Long leftPush(K key, V value);
        //将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
        template.opsForList().leftPush("list", "java");
        template.opsForList().leftPush("list", "python");
        template.opsForList().leftPush("list", "c++");

        //批量把一个集合插入到列表中
        String[] stringarrays = new String[]{"1", "2", "3"};
        template.opsForList().leftPushAll("listarray", stringarrays);
        System.out.println(template.opsForList().range("listarray", 0, -1));

        List<Object> strings = new ArrayList<Object>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        template.opsForList().leftPushAll("listcollection4", strings);
        System.out.println(template.opsForList().range("listcollection4", 0, -1));

        //Long leftPushIfPresent(K key, V value);
        //只有存在key对应的列表才能将这个value值插入到key所对应的列表中
        System.out.println(template.opsForList().leftPushIfPresent("leftPushIfPresent", "aa"));
        System.out.println(template.opsForList().leftPushIfPresent("leftPushIfPresent", "bb"));
        System.out.println(template.opsForList().leftPush("leftPushIfPresent", "aa"));
        System.out.println(template.opsForList().leftPushIfPresent("leftPushIfPresent", "bb"));

        //Long rightPush(K key, V value);
        //将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
        template.opsForList().rightPush("listRight", "java");
        template.opsForList().rightPush("listRight", "python");
        template.opsForList().rightPush("listRight", "c++");

        //void set(K key, long index, V value);
        //在列表中index的位置设置value值
        System.out.println(template.opsForList().range("listRight",0,-1));
        template.opsForList().set("listRight",1,"setValue");
        System.out.println(template.opsForList().range("listRight",0,-1));

        //Long remove(K key, long count, Object value);
        //从存储在键中的列表中删除等于值的元素的第一个计数事件。
        //计数参数以下列方式影响操作：
        //count> 0：删除等于从头到尾移动的值的元素。
        //count <0：删除等于从尾到头移动的值的元素。
        //count = 0：删除等于value的所有元素
        System.out.println(template.opsForList().range("listRight",0,-1));
        template.opsForList().remove("listRight",1,"setValue");//将删除列表中存储的列表中第一次次出现的“setValue”。
        System.out.println(template.opsForList().range("listRight",0,-1));

        //V index(K key, long index);
        //根据下表获取列表中的值，下标是从0开始的
        System.out.println(template.opsForList().range("listRight",0,-1));
        System.out.println(template.opsForList().index("listRight",1));

        //V rightPop(K key);
        //弹出最右边的元素，弹出之后该值在列表中将不复存在
    }

    public void hashMapList(){

    }

    public Map<String, Object> testList() {
        int index[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List list = Arrays.asList(index);
        //Redis中链表从左到右的顺序是10，9，9，7......
        template.opsForList().leftPushAll("list1", list);
        Collections.reverse(list);
        //Redis中链表从右到左的顺序是10，9，9，7...... 相反
        template.opsForList().rightPushAll("list2", list);
        //绑定链表2
        BoundListOperations listOps = template.boundListOps("list2");
        //从右边弹出一个成员
        Object result1 = listOps.rightPop();
        //获取定位元素
        Object result2 = listOps.index(1);
        //从右边插入链表
        listOps.leftPush("0");
        //链表长度
        System.out.println(listOps.size());
        return null;
    }

//    public void testObj() throws Exception {
//        UserVo userVo = new UserVo();
//        userVo.setAddress("上海");
//        userVo.setName("测试dfas");
//        userVo.setAge(123);
//        ValueOperations<String, Object> operations = template.opsForValue();
//        redisService.expireKey("name", 20, TimeUnit.SECONDS);
//        String key = RedisKeyUtil.getKey(UserVo.Table, "name", userVo.getName());
//        UserVo vo = (UserVo) operations.get(key);
//        System.out.println(vo);
//    }
//
//    public void testValueOption() throws Exception {
//        ValueOperations<String, Object> valueOperations = template.opsForValue();
//        UserVo userVo = new UserVo();
//        userVo.setAddress("上海");
//        userVo.setName("jantent");
//        userVo.setAge(23);
//        valueOperations.set("test", userVo);
//        System.out.println(valueOperations.get("test"));
//    }
//
//    public void testSetOperation() throws Exception {
//        SetOperations<String, Object> setOperations = template.opsForSet();
//        UserVo userVo = new UserVo();
//        userVo.setAddress("北京");
//        userVo.setName("jantent");
//        userVo.setAge(23);
//        UserVo auserVo = new UserVo();
//        auserVo.setAddress("n柜昂周");
//        auserVo.setName("antent");
//        auserVo.setAge(23);
//        setOperations.add("user:test", userVo, auserVo);
//        Set<Object> result = setOperations.members("user:test");
//        System.out.println(result);
//    }
//
//    public void HashOperations() throws Exception {
//        HashOperations<String, String, Object> hashOperations = template.opsForHash();
//        UserVo userVo = new UserVo();
//        userVo.setAddress("北京");
//        userVo.setName("jantent");
//        userVo.setAge(23);
//        hashOperations.put("hash:user", userVo.hashCode() + "", userVo);
//        System.out.println(hashOperations.get("hash:user", userVo.hashCode() + ""));
//    }
//
//    public void ListOperations() throws Exception {
//        ListOperations<String, Object> listOperations = template.opsForList();
//        UserVo userVo = new UserVo();
//        userVo.setAddress("北京");
//        userVo.setName("jantent");
//        userVo.setAge(23);
////        listOperations.leftPush("list:user",userVo);
////        System.out.println(listOperations.leftPop("list:user"));
//        // pop之后 值会消失
//        System.out.println(listOperations.leftPop("list:user"));
//    }
}
