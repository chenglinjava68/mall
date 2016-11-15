package com.plateno.booking.internal.email.model;

/**
 * 发送消息内容基类
 * @author mogt
 * @date 2016年11月14日
 */
public class BaseContent {
	
	/**
	 * 短信发送的标识（一般为订单号）
	 */
	private String objectNo;

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}
}
