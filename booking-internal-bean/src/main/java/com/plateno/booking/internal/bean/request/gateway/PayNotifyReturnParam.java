package com.plateno.booking.internal.bean.request.gateway;


/**
 * 网关支付回调通知bean
 * 
 * @author user
 *
 */
public class PayNotifyReturnParam {
	
	private String code;			//响应码
	private String orderNo;			//商户订单号
	private String message;			//响应消息
	private String signData;		//签名
	private String referenceId;		//外部订单号
	private Integer orderAmount;	//订单金额
	private String ext1;			//扩展字段1
	private String ext2;			//扩展字段2
	private String ext3;			//扩展字段3
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSignData() {
		return signData;
	}
	public void setSignData(String signData) {
		this.signData = signData;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
}