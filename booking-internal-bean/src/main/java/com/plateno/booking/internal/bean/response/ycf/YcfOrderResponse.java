package com.plateno.booking.internal.bean.response.ycf;

import java.io.Serializable;

/**
 * 要出发创建订单返回参数
 * @author yi.wang
 * @date   2016年6月17日上午11:30:45
 * @version 1.0
 * @Description
 */
public class YcfOrderResponse implements Serializable {

	private static final long serialVersionUID = 8987782141536798858L;

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
