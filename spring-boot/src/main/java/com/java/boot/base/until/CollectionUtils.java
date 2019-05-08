package com.java.boot.base.until;

import org.apache.ibatis.mapping.ParameterMapping;

import java.util.List;

/**
 * @author xuweizhi
 * @date 2019/04/23 10:12
 */
public class CollectionUtils {
    public static boolean isNotEmpty(List<ParameterMapping> parameterMappings) {
        return parameterMappings.size() > 0;
    }
}
