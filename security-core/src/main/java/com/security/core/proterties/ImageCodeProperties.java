package com.security.core.proterties;

/**
 * 验证码的默认配置
 *
 * @author xuweizhi
 * @date 2019/05/15 19:56
 */
public class ImageCodeProperties extends SmsCodeProperties {

    /**
     * 验证码的宽度
     */
    private int width = 67;

    /**
     * 验证码的高度
     */
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}
