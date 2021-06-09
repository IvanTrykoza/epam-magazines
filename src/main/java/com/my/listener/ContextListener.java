package com.my.listener;

import com.my.jdbc.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBManager dbManager = DBManager.getInstance();

        ServletContext context = sce.getServletContext();

        String path = context.getRealPath("WEB-INF/app.log");
        System.out.println("path ==> " + path);

        Logger log = LogManager.getLogger(ContextListener.class);
        log.info("info");
    }
}