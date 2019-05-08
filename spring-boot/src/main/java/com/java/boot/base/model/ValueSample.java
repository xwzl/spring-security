package com.java.boot.base.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件属性注入
 *
 * @author xuweizhi
 * @date 2019/04/22 15:43
 */
@Component
public class ValueSample {

    @Value("${com.xwz.sample}")
    private String sample;

    @Value("${com.xwz.desc}")
    private String desc;

    @Value("${com.xwz.value}")
    private String value;

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueSample{" +
                "sample='" + sample + '\'' +
                ", desc='" + desc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
