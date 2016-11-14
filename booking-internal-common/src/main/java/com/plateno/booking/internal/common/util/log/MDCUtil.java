package com.plateno.booking.internal.common.util.log;

import java.util.UUID;

import org.apache.log4j.MDC;

/**
 * MDC工具
 * @author mogt
 * @date 2016年11月14日
 */
public class MDCUtil {
	
	private final static String TRACE_ID_KEY = "trace_id";
	
	/**
	 * 添加线程标志
	 */
    public static void addTraceId() {
    	Object traceId = MDC.get(TRACE_ID_KEY);
        if(traceId == null) {
       	 	traceId = UUID.randomUUID().toString().replace("-","");
            MDC.put(TRACE_ID_KEY, traceId);
        }
    }
    
    /**
	 * 去除线程标志
	 */
    public static void removeTraceId() {
    	MDC.remove(TRACE_ID_KEY);
    }

}
