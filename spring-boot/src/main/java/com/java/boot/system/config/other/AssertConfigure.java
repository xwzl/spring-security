package com.java.boot.system.config.other;

import com.java.boot.base.model.Goods;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot通过@ConditionalOnProperty来控制Configuration是否生效
 * <p>
 * https://www.jianshu.com/p/68a75c093023
 *
 * @author xuweizhi
 * @since  2019/04/22 14:39
 */
@Configuration
public class AssertConfigure {

    @Bean
    public Goods getGoods() {
        Goods goods = new Goods();
        goods.setName("货物");
        return goods;
    }
}
