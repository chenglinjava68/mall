package com.plateno.booking.internal.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class MapUtil{
	
	public static Map<String,String> clearEmpty(Map<String,String> src){
		Map<String,String> result = new HashMap<String,String>();
		Set<Entry<String,String>> set = src.entrySet();
		for(Entry<String,String> e:set){
			if(StringUtils.isNotBlank(e.getValue())){
				result.put(e.getKey(),e.getValue());
			}
		}
		return result;
	}
	

}
