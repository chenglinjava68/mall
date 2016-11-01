package com.plateno.booking.internal.bean.request.custom;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class PHPNotifyParam {

	@NotNull(message = "铂涛订单号,不能为空")
	@NotEmpty(message = "铂涛订单号,不能为空")
	private String orderNo;		//订单ID 
	
	@NotNull(message = "铂涛支付流水号,不能为空")
	@NotEmpty(message = "铂涛支付流水号,不能为空")
	private String serialId;	//流水号

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

}
