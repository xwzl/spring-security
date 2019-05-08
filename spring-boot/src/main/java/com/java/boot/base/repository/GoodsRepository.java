package com.java.boot.base.repository;

import com.java.boot.base.entity.GoodsInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author xuweizhi
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo, Long> {

}