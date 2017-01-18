package com.plateno.booking.internal.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogUtils{

	/**
	 * 统计发起请求参数和响应时间
	 */
	private static final Logger countTimeLogger = LoggerFactory.getLogger("countTimeLogger");

	/**
	 * 发起http日志记录
	 */
	private static final Logger httpLogger = LoggerFactory.getLogger("httpLogger");

	/**
	 * 系统出错日志记录
	 */
	private static final Logger sysErrorLogger = LoggerFactory.getLogger("sysErrorLogger");
	
	
	/**
	 * 系统正常日志记录
	 */
	private static final Logger sysInfoLogger = LoggerFactory.getLogger("sysInfoLogger");
	
	/**
	 * 分布式事务错误，需要人工介入
	 */
	public static final Logger DISPERSED_ERROR_LOGGER = LoggerFactory.getLogger("dispersedErrorLogger");

	public static void countTimeLoggerInfo(String msg) {
		countTimeLogger.info(msg);
	}

	public static void countTimeLoggerError(String msg,Object e) {
		countTimeLogger.error(msg,e);
	}

	public static void httpLoggerInfo(String msg) {
		httpLogger.info(msg);
	}

	public static void httpLoggerError(String msg,Object e) {
		httpLogger.error(msg,e);
	}

	public static void sysErrorLoggerInfo(String msg) {
		sysErrorLogger.info(msg);
	}

	public static void sysErrorLoggerError(String msg,Object e) {
		sysErrorLogger.error(msg,e);
	}
	
	public static void sysLoggerInfo(String msg) {
		sysInfoLogger.info(msg);
	}
	

}