package com.security.core.validate.core;

import com.security.core.constant.SecurityConstants;

/**
 * 验证码类型
 *
 * @author xuweizhi
 * @date 2019/05/16 9:54
 */
public enum ValidateCodeType {

    /**
     * 短信验证码,其值一定要与 ValidateCodeProcessor 的实现类名称截取 CodeProcessor 一致
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     */
    public abstract String getParamNameOnValidate();

}
