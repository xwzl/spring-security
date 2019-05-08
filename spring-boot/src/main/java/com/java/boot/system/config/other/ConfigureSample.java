package com.java.boot.system.config.other;

import com.java.boot.base.model.Book;
import com.java.boot.base.controller.RestControllerSample;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * <h2> @Conditional</h2>
 * <p>
 * 解释：@Configuration标注在类上，相当于把该类作为spring的xml配置文件中的 beans ，作用为：配置spring容器(应用上下文)
 * <p>
 * 指定 @Conditional(ConditionSample.class)指定条件，若满足 ConditionSample 类中的条件，则该类下面的 Bean 加入该容器，
 * 当然 @Conditional 也可用于方法或者类上。
 * <p>
 * 如果ConditionSample中的方法返回为false，说明 Bean 的类没有被注册到容器，但是在{@link RestControllerSample}中有Book
 * 的引用，会报错。
 *
 * @author xuweizhi
 * @since  2019/04/22 14:07
 */
@Configuration
@Conditional(ConditionSample.class)
public class ConfigureSample {

    @Bean
    public Book getBook() {
        Book book = new Book();
        book.setBookName("Faker 封神之战分析");
        return book;
    }

}
