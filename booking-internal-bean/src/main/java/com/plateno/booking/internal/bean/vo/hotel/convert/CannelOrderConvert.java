package com.plateno.booking.internal.bean.vo.hotel.convert;

import java.io.Serializable;

import com.plateno.booking.internal.bean.vo.hotel.base.HotelBaseParam;

public class CannelOrderConvert extends HotelBaseParam implements Serializable {
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
