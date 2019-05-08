package com.java.boot.base.repository;

import com.java.boot.base.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * 文章 Repository  泛型的参数分别是实体类型和主键类型
 *
 * @author xuweizhi
 * @since 2018-04-25 17:01
 **/
@Component
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {

}