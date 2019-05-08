package com.java.boot.base.repository;

import com.java.boot.base.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Spring Data 的强大之处，就在于你不用写任何DAO处理，自动根据方法名或类的信息进行CRUD操作。只要你定义一个接口，
 * 然后继承Repository提供的一些子接口，就能具备各种基本的CRUD功能。
 * <p>
 * 定义ItemRepository 接口
 * <p>
 * Item:为实体类
 * Long:为Item实体类中主键的数据类型
 *
 * @author xuweizhi
 * @date 2019/04/27 23:38
 */
@Component
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    /**
     * Spring Data 的另一个强大功能，是根据方法名称自动实现功能。
     * <p>
     * 比如：你的方法名叫做：findByTitle，那么它就知道你是根据title查询，然后自动帮你完成，无需写实现类。
     * <p>
     * 当然，方法名称要符合一定的约定：详情见 SpringBootData 规则
     * <p>
     * 根据价格区间查询
     *
     * @param price1 从 price1
     * @param price2 到 price2
     * @return       检索条目
     */
    List<Item> findByPriceBetween(double price1, double price2);

}
