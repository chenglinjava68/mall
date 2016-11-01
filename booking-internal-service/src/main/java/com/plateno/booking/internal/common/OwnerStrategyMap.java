package com.plateno.booking.internal.common;

import java.util.Map;

import com.plateno.booking.internal.service.thirdService.base.ThirdApiService;

/**
 * 获取第三方服务对象
 * 
 * @author user
 *
 */
public class OwnerStrategyMap {
	
	
	private Map<String, ThirdApiService> findOwnerStrategyMap ;

	public Map<String, ThirdApiService> getFindOwnerStrategyMap() {
		return findOwnerStrategyMap;
	}

	public void setFindOwnerStrategyMap(Map<String, ThirdApiService> findOwnerStrategyMap) {
		this.findOwnerStrategyMap = findOwnerStrategyMap;
	}
}
