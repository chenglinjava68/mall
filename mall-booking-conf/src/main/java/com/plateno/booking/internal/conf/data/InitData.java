package com.plateno.booking.internal.conf.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化所有的缓存数据
 * @author mogt
 * @date 2016年11月29日
 */
public class InitData {
	
	private static Logger logger = LoggerFactory.getLogger(InitData.class);
	
	/**
	 * 初始化所有的缓存数据
	 */
	public synchronized static void initData() {
		
		logger.info("MLogisticsTypeData.loadData() start...");
		
		MLogisticsTypeData.loadData();
		
		logger.info("MLogisticsTypeData.loadData() end.");
	}
}
