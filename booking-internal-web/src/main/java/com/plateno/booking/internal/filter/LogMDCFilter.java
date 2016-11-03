package com.plateno.booking.internal.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.MDC;

/**
 * Log mdc 存储一些线程相关的变量
 * @author mogt
 * @date 2016年10月28日
 */
public class LogMDCFilter implements Filter {
	
	private final static String TRACE_ID_KEY = "trace_id";

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			addTraceId();
			chain.doFilter(request, response);
		} finally {
			removeTraceId();
		}
		
	}

	/**
	 * 添加线程标志
	 */
    private static void addTraceId() {
    	Object traceId = MDC.get(TRACE_ID_KEY);
        if(traceId == null) {
       	 	traceId = UUID.randomUUID().toString().replace("-","");
            MDC.put(TRACE_ID_KEY, traceId);
        }
    }
    
    /**
	 * 去除线程标志
	 */
    private static void removeTraceId() {
    	MDC.remove(TRACE_ID_KEY);
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
