package com.plateno.booking.internal.dao.pojo.sms;

import java.util.Map;

import com.plateno.booking.internal.bean.config.Config;

public class SmsMessageReq {
	
	//接入id 和头的X-AUTH-HEADER保持一致
	private Integer appId = Integer.parseInt(Config.SMS_SERVICE_APPID);
	
	// 这个调用系统做标记
	private Integer sellerId = 0;
	
	private Integer type;
	
	private String phone;
	
	private Map<String, String> params;

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Map<String, String> getParams() {
		return this.params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}