package com.systech.mss.web;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener
public class WebConfigurer implements ServletContextListener {

    @Inject
    private Logger log;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Web application fully configured");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
