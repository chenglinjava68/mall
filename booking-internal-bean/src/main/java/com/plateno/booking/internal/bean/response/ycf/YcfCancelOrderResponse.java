package com.plateno.booking.internal.bean.response.ycf;

import java.io.Serializable;

/**
 * 要出发申请取消订单返回参数
 * @author yi.wang
 * @date 2016年6月17日上午11:30:45
 * @version 1.0
 * @Description
 */
public class YcfCancelOrderResponse implements Serializable {

	private static final long serialVersionUID = 3737964456638040639L;

	//订单状态
	private Integer orderStatus;

	//【要】订单编号
	private String orderId;

	//异步处理状态,0：同步即时处理，1：异步处理；为0时OrderStatus和OrderId为必填项
	private Integer async;

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

	public Integer getAsync() {
		return async;
	}

	public void setAsync(Integer async) {
		this.async = async;
	}

}
