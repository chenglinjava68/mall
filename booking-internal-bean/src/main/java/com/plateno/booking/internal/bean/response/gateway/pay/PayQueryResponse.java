package com.plateno.booking.internal.bean.response.gateway.pay;

import com.plateno.booking.internal.bean.response.gateway.base.BaseParam;

/**
 * 支付查询响应bean
 * 
 * @author user
 *
 */
public class PayQueryResponse extends BaseParam {

	private Integer orderAmount;	//支付金额(单位：分)
	private String ext1;
	private String ext2;
	private String ext3;

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
