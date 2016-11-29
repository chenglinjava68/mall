package com.plateno.booking.internal.conf.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plateno.booking.internal.base.mapper.MLogisticsTypeMapper;
import com.plateno.booking.internal.base.pojo.MLogisticsType;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.conf.vo.LogisticsTypeInfo;
import com.plateno.booking.internal.interceptor.adam.common.util.context.SpringContextUtils;

/**
 * 物流信息
 * @author mogt
 * @date 2016年11月29日
 */
public class LogisticsTypeData {
	
	protected final static Logger logger = LoggerFactory.getLogger(LogisticsTypeData.class);

	/**
	 * 数据Map
	 */
	private static Map<Integer, String> dataMap = new HashMap<>();
	/**
	 * 数据List
	 */
	private static List<LogisticsTypeInfo> dataList = new ArrayList<>();
	
	/**
	 * 从数据库中加载数据到内存
	 */
	public static void loadData() {
		
		MLogisticsTypeMapper mapper = SpringContextUtils.getBean(MLogisticsTypeMapper.class);
		List<MLogisticsType> list = mapper.listAll();
		
		//封装map
		Map<Integer, String> map = new HashMap<>();
		List<LogisticsTypeInfo> infoList = new ArrayList<>();
		
		try {
			logger.info("MLogisticsType list:{}", JsonUtils.toJsonString(list));
		} catch (Exception e) {
		}
		
		for(MLogisticsType type : list) {
			map.put(type.getType(), type.getDescription());
			
			LogisticsTypeInfo info = new LogisticsTypeInfo();
			info.setType(type.getType());
			info.setDescription(type.getDescription());
			infoList.add(info);
		}
		
		dataList = infoList;
		dataMap = map;
	}

	public static Map<Integer, String> getDataMap() {
		
		if(dataMap == null || dataMap.isEmpty()) {
			synchronized (LogisticsTypeData.class) {
				if(dataMap == null || dataMap.isEmpty()) {
					loadData();
				}
			}
		}
		
		if(dataMap == null) {
			return new HashMap<>();
		}
		
		return dataMap;
	}

	public static List<LogisticsTypeInfo> getDataList() {
		
		if(dataList == null || dataList.isEmpty()) {
			synchronized (LogisticsTypeData.class) {
				if(dataList == null || dataList.isEmpty()) {
					loadData();
				}
			}
		}
		
		if(dataList == null) {
			return new ArrayList<LogisticsTypeInfo>(0);
		}
		
		return dataList;
	}
	
	public static boolean hasType(Integer type) {
		if(type == null) {
			return false;
		}
		
		for(LogisticsTypeInfo info : getDataList()) {
			if(type.equals(info.getType())) {
				return true;
			}
		}
		
		return false;
	}

}
