package com.plateno.booking.internal;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.common.configload.ConfigLoader;
import com.plateno.booking.internal.filter.LogMDCFilter;



/**
 * @author user
 * 
 *         jetty 启动
 *
 */
public class JettyServer {

    private static final Logger logger = Logger.getLogger(JettyServer.class);

    public static void main(String[] args) throws Exception {
        try {
            logger.info("start");
            InetSocketAddress address = new InetSocketAddress(Config.LOCAL_LISTEN_IP, 9577);// post:8083
            Server server = new Server(address);
            ServletContextHandler context =
                    new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
            context.setContextPath("/booking-internal-web");
            DispatcherServlet servlet = new DispatcherServlet();
            servlet.setContextConfigLocation("classpath:applicationContext.xml");
            context.addServlet(new ServletHolder(servlet), "/");

            LogMDCFilter logMDCFilter = new LogMDCFilter();
            FilterHolder holder = new FilterHolder(logMDCFilter);
            context.addFilter(holder, "/*", null);

            com.alibaba.druid.support.http.StatViewServlet druidServlet =
                    new com.alibaba.druid.support.http.StatViewServlet();
            ServletHolder servletHolder = new ServletHolder(druidServlet);
            servletHolder.setInitParameter("resetEnable", "true");
            // servletHolder.setInitParameter("loginUsername", "druid");
            // servletHolder.setInitParameter("loginPassword", "da453e261fe94650");
            context.addServlet(servletHolder, "/druid/*");
            server.setHandler(context);

            server.start();

            // 配置加载
            WebApplicationContext springContext = servlet.getWebApplicationContext();
            ConfigLoader loader = springContext.getBean(ConfigLoader.class);
            loader.init();

            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
