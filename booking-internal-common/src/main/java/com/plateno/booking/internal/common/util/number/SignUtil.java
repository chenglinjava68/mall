package com.plateno.booking.internal.common.util.number;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class SignUtil{
	
	private static Logger log = Logger.getLogger(SignUtil.class);

	public static String sign(Map<String,String> map){
		String src = createLink(map);
		return sign(src);
	}
	
	public static String sign(String src){
		try{
			log.debug(String.format("原文[%s]",src));
			String hash = MD5Util.encode(src);
			log.debug(String.format("签名[%s]",hash));
			return hash;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return "";
	}

	public static String createLink(Map<String,String> map){
		SortedMap<String,String> sort=new TreeMap<String,String>(); 
		for(Entry<String,?> entry : map.entrySet()){
			String key = entry.getKey();
			String value = (String)entry.getValue();
			if(value==null || "".equals(value.trim()))
				continue;
			sort.put(key,value);
		}
		
		StringBuffer buf = new StringBuffer();
		for(Entry<String,String> entry:sort.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();

			if(buf.length()==0){
				buf.append(key).append("=").append(value);
			}else{
				buf.append("&").append(key).append("=").append(value);
			}
		}
		return buf.toString();
	}

}
