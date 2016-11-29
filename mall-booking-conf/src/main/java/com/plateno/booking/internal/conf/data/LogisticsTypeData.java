package com.plateno.booking.internal.conf.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plateno.booking.internal.base.mapper.MLogisticsTypeMapper;
import com.plateno.booking.internal.base.pojo.MLogisticsType;
import com.plateno.booking.internal.conf.vo.LogisticsTypeInfo;
import com.plateno.booking.internal.interceptor.adam.common.util.context.SpringContextUtils;

/**
 * 物流信息
 * @author mogt
 * @date 2016年11月29日
 */
public class LogisticsTypeData {

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
		
		return dataList;
	}

}
