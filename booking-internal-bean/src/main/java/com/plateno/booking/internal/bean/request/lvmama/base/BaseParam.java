package com.plateno.booking.internal.bean.request.lvmama.base;
import java.net.URLEncoder;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.util.MD5Util;

public class BaseParam {

	private String appKey;
	private String messageFormat;
	private String timestamp;
	private String sign;
	
	/*public BaseParam(DataFormat messageFormat){
		long time = System.currentTimeMillis();
		try {
			this.appKey = URLEncoder.encode(Config.APPKEY, "UTF-8");
			this.timestamp = URLEncoder.encode(time+"", "UTF-8");
			this.sign = URLEncoder.encode(MD5Util.encode(Config.SECRET+time+Config.SECRET), "UTF-8");
			this.messageFormat = messageFormat.getFormat();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*public BaseParam(){
		long time = System.currentTimeMillis();
		try {
			this.appKey = URLEncoder.encode(Config.APPKEY, "UTF-8");
			this.timestamp = URLEncoder.encode(time+"", "UTF-8");
			this.sign = URLEncoder.encode(MD5Util.encode(Config.SECRET+time+Config.SECRET), "UTF-8");
			this.messageFormat = "json";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public enum DataFormat{
		XML(0,"xml"),
		JSON(1,"json");
		
		private final Integer code;
		private final String format;
		
		
		private DataFormat(Integer code, String format) {
			this.code = code;
			this.format = format;
		}
		
		public static DataFormat getByCode(Integer c){
			for(DataFormat data : DataFormat.values()){
				if(data.getCode() == c){
					return data;
				}
			}
			return null;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getFormat() {
			return format;
		}
		
	}	

}