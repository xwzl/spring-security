package com.java.boot.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * <h2>Spring Data通过注解来声明字段的映射属性，有下面的三个注解：</h2>
 * <h3>1、@Document 作用在类，标记实体类为文档对象，一般有两个属性</h3>
 * <ul>
 *      <li>indexName：对应索引库名称</li>
 *      <li>type：对应在索引库中的类型</li>
 *      <li>shards：分片数量，默认5</li>
 *      <li>replicas：副本数量，默认1</li>
 * </ul>
 * <h3>2、Id 作用在成员变量，标记一个字段作为id主键</h2>
 * <h3>3、@Field 作用在成员变量，标记为文档的字段，并指定字段映射属性：</h3>
 * <ul>
 *     <li>type：字段类型，是枚举：FieldType，可以是text、long、short、date、integer、object等
 *     <ul>
 *         <li>text：存储数据时候，会自动分词，并生成索引</li>
 *         <li>keyword：存储数据时候，不会分词建立索引</li>
 *         <li>Numerical：数值类型，分两类：
 *               <ul>
 *                   <li>基本数据类型：long、interger、short、byte、double、float、half_float</li>
 *                   <li>浮点数的高精度类型：scaled_float,需要指定一个精度因子，比如10或100。elasticsearch会把真实值乘以这个因子后存储，取出时再还原。</li>
 *               </ul>
 *         </li>
 *         <li>Date：日期类型。elasticsearch可以对日期格式化为字符串存储，但是建议我们存储为毫秒值，存储为long，节省空间。</li>
 *     </ul>
 *     </li>
 *     <li>index：是否索引，布尔类型，默认是true</li>
 *     <li>store：是否存储，布尔类型，默认是false</li>
 *     <li>analyzer：分词器名称，这里的ik_max_word即使用ik分词器</li>
 * </ul>
 * @author xuweizhi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "item",type = "docs", shards = 1, replicas = 0)
public class Item {

    /**
     * 注解必须是springframework包下的org.springframework.data.annotation.Id
     */
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(index = false, type = FieldType.Keyword)
    private String images;


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", images='" + images + '\'' +
                '}';
    }
}
