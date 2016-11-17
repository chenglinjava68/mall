package com.plateno.booking.internal.job.order;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.servlet.DispatcherServlet;

import com.plateno.booking.internal.bean.config.Config;



/**
 * @author user
 * 
 *  jetty 启动
 *
 */
public class JettyServer {
	
	private static final Logger logger = Logger.getLogger(JettyServer.class);
	public static void main(String[] args) throws Exception{
		try {
        	logger.info("start");
			InetSocketAddress address = new InetSocketAddress(Config.LOCAL_LISTEN_IP,9579);//post:9971
			Server server = new Server(address);
			ServletContextHandler context  = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
			DispatcherServlet servlet = new DispatcherServlet();
			servlet.setContextConfigLocation("classpath:applicationContext.xml");
			context.addServlet(new ServletHolder(servlet),"/");
			server.setHandler(context);
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
