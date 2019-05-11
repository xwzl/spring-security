package com.java.tool.bean.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xuweizhi
 * @since 2019/05/07 9:54
 */
@RestController
public class MyController {

    /**
     * 1. 依次注释 myImpl1,defaultMyImpl1,myPlusImpl1 的 @Qualifier注解，启动程序。
     * 2. 按类型注入是指 private My myImpl1 中的 myImpl1 的值注入，因为 myImpl1 并未注入到 IOC 容器或者在 IOC
     *    容器中并没有找到 myImpl1 对应的 bean, 所以注入失败，但是想要成功注入,如何操作呢？
     * 3. 其他变量的注解依次打开，但是 myImpl1 @Qualifier 注释依然关闭，在 MyImpl 类上的@Componet注解改变为
     *    ,@Component("myImpl1"),再次启动。这次启动没有报错，是因为我们为 MyImpl 类添加了一个别名，所以myIMpl1
     *    能在 IOC 找到对应的 bean 对象.
     * 4. 那问题来呢？ myImpl 这个类名还能按类型注入myImpl? 打开myImpl的注解，答案是肯定的，不能；会报步骤一的异常。
     *
     * 总结：
     *
     *  如果没有为 bean 对象指定别名，成员变量的名称必须是类名加首字母小写，如 private My myImpl;
     *  如果为 bean 对象指定了别名，成员变量的名称一定要别名一致或者用@Qulifer 或者 @Resource 指定需要注入的 bean  对象；
     */
    @Autowired
    //@Qualifier("myImpl")
    private My myImpl;

    //@Autowired
    //@Qualifier("myImpl1")
    //private My myImpl;

    /**
     * 注释 @Qualifier
     */
    @Autowired
    @Qualifier("defaultMyImpl")
    private My defaultMyImpl1;

    /**
     * 注释 @Resource ,打开@Autowired 看效果
     */
    //@Autowired
    @Resource(name = "myPlusImpl")
    private My myPlusImpl1;

    //public MyController(MyImpl myImpl, DefaultMyImpl defaultMyImpl, MyPlusImpl myPlusImpl) {
    //    myImpl = myImpl;
    //    this.defaultMyImpl = defaultMyImpl;
    //    this.myPlusImpl = myPlusImpl;
    //}

    @RequestMapping("index")
    public void say() {
        defaultMyImpl1.info();
        myPlusImpl1.info();
        myImpl.info();
    }
}
