package com.security.demo.config.exception;

import com.security.demo.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局处理异常，当异常出现时
 * @author xuweizhi
 * @since 2019/05/12 12:17
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 全局处理 UserExistNotException 异常,返回状态码 500
     * 出现错误时返回的json数据
     * {
     *     "id": "2",
     *     "message": "User not exist"
     * }
     */
    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> handleUserNotExistException(UserNotExistException ex){
        Map<String,String> result = new HashMap();
        result.put("id",ex.getId());
        result.put("message",ex.getMessage());
        return result;
    }
}
