package com.java.boot.base.until;

import com.java.boot.base.enums.LogEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地日志参考类
 *
 * @author xwz
 */
public class LogUtils {


    /**
     * 获取业务日志logger
     */
    public static Logger getBusinessLogger() {
        return LoggerFactory.getLogger(LogEnum.BUSINESS.getCategory());
    }

    /**
     * 获取平台日志logger
     */
    public static Logger getPlatformLogger() {
        return LoggerFactory.getLogger(LogEnum.PLATFORM.getCategory());
    }

    /**
     * 获取数据库日志logger
     */
    public static Logger getDBLogger() {
        return LoggerFactory.getLogger(LogEnum.DB.getCategory());
    }


    /**
     * 获取异常日志logger
     */
    public static Logger getExceptionLogger() {
        return LoggerFactory.getLogger(LogEnum.EXCEPTION.getCategory());
    }


}