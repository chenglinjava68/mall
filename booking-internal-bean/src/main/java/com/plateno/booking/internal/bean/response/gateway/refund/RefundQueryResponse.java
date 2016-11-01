package com.plateno.booking.internal.bean.response.gateway.refund;

import com.plateno.booking.internal.bean.response.gateway.base.BaseParam;

/**
 * 退款查询响应bean
 * 
 * @author user
 *
 */
public class RefundQueryResponse extends BaseParam {
	private Integer orderAmount;		//支付金额(单位：分)

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

}
