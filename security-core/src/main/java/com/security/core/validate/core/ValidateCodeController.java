package com.security.core.validate.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author xuweizhi
 * @date 2019/05/15 18:40
 */
@RestController
public class ValidateCodeController {

    /**
     * 验证码的key
     */
    public static final String SESSION_KEY = "SESSION_KEY_FOR_CODE_image";

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        validateCodeProcessors.get(type + "ValidateCodeProcessor").create(new ServletWebRequest(request, response));
    }


    ///**
    // * 键值对
    // */
    //public final static String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    //
    ///**
    // * 图形验证码的生成器
    // */
    //@Autowired
    //private ValidateCodeGenerator imageCodeGenerator;
    //
    ///**
    // * 短信验证的生成器
    // */
    //@Autowired
    //private ValidateCodeGenerator smsCodeGenerator;
    //
    //@Autowired
    //private SmsCodeSender smsCodeSender;
    //
    ///**
    // * 当前请求的缓存信息
    // */
    //private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    //
    ///**
    // * 图形验证码
    // */
    //@GetMapping("/code/image")
    //public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //
    //    // 1. 构建图片独享
    //    ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
    //
    //    // 2. 设置缓存，用于缓存校验
    //    sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
    //
    //    // 3. 返回图片信息
    //    ImageIO.write(imageCode.getImageCode(), "JPEG", response.getOutputStream());
    //}
    //
    ///**
    // * 短信验证码
    // */
    //@GetMapping("/code/sms")
    //public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
    //
    //    // 1. 生成状态码
    //    ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
    //
    //    // 2. 设置缓存，用于缓存校验
    //    sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
    //    // 请求必须包含参数
    //    String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
    //
    //    smsCodeSender.send(mobile,smsCode.getValidateCode());
    //}

}
