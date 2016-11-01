package com.plateno.booking.internal.bean.response.ycf;

import java.io.Serializable;

/**
 * 要出发支付返回参数
 * @author yi.wang
 * @date 2016年7月4日下午4:53:19
 * @version 1.0
 * @Description
 */
public class YcfPayOrderResponse implements Serializable {

	private static final long serialVersionUID = 2895042321443991108L;

	//订单状态
	private Integer orderStatus;

	//【要】订单编号
	private String orderId;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
