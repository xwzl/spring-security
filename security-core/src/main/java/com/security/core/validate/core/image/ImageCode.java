package com.security.core.validate.core.image;

import com.security.core.validate.core.sms.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码类
 *
 * @author xuweizhi
 * @date 2019/05/15 18:36
 */
public class ImageCode  extends ValidateCode {

    /**
     * 图片
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
