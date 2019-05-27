package com.security.core.validate.core.image;

import com.security.core.validate.core.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码类
 *
 * @author xuweizhi
 * @date 2019/05/15 18:36
 */
public class ImageCode  extends ValidateCode {

    private static final long serialVersionUID = 8418486433439613686L;

    /**
     * 图片无法被序列化，因此不放入 session 中
     */
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }


}
