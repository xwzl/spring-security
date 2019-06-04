package com.data.dynamic.service.impl;

import com.data.dynamic.model.Product;
import com.data.dynamic.mapper.ProductMapper;
import com.data.dynamic.service.ProductService;
import com.data.dynamic.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuweizhi
 * @since 2019-06-04
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product> implements ProductService {

}
