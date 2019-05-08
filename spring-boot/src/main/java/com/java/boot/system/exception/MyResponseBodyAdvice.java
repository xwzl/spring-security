package com.java.boot.system.exception;

import com.java.boot.base.enums.ResultEnum;
import com.java.boot.base.vo.BaseResultVO;
import com.java.boot.base.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 一定要指明切面
 *
 * @author xuweizhi
 */
@Slf4j
@RestControllerAdvice(value = "com.java.boot")
@SuppressWarnings({"unchecked","rawtypes"})
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    /**
     * 判断支持的类型
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean supports(MethodParameter returnType, Class converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    /**
     * 对结果进行封装，或者进行加密处理
     */
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        /*if (body instanceof ErrorInfo) {
            ResultVO<ErrorInfo<Object>> restResult = new ResultVO<>();
            restResult.setCode(ResultEnum.FAILURE.getCode());
            restResult.setMessage(ResultEnum.FAILURE.getMessage());
            restResult.setData((ErrorInfo<Object>) body);
            return restResult;
        }*/
        if (body instanceof BaseResultVO) {
            return body;
        }

        if (body instanceof ResultVO) {
            ResultVO<Object> resultVO = (ResultVO<Object>) body;
            resultVO.setCode(ResultEnum.SUCCESS.getCode());
            resultVO.setMessage(ResultEnum.SUCCESS.getMessage());
            resultVO.setData(body);
            return resultVO;
        } else {
            ResultVO<Object> resultVO = new ResultVO<>();
            resultVO.setCode(ResultEnum.SUCCESS.getCode());
            resultVO.setMessage(ResultEnum.SUCCESS.getMessage());
            resultVO.setData(body);
            return resultVO;
        }
        //return body;
    }
}