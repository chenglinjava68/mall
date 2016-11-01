package com.plateno.booking.internal.bean.vo.hotel.request;

import java.io.Serializable;

/**
 * 酒店预定取消订单bean
 * 
 * @author user
 *
 */
public class CannelOrderRequest implements Serializable {
	private static final long serialVersionUID = -1052928707328655339L;
	private String orderCode; // 订单编码
	private String remarks; // 备注

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
