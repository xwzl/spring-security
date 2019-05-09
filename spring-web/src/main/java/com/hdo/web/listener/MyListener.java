package com.hdo.web.listener;

import com.hdo.web.filter.MyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @ClassName MyListener
 * @Author XWZ
 * @Description
 * @Date 2018-08-26 15:20 星期日 SpringBootProgram
 * @VERSION 1.0.0
 **/
public class MyListener implements ServletContextListener {

    Logger logger = LoggerFactory.getLogger(MyListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Web contextInitialized Initialized ...................");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Web contextDestroyed Destroyed ...................");
    }
}
