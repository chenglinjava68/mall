package com.plateno.booking.internal.bean.vo.hotel.convert;

import com.plateno.booking.internal.bean.vo.hotel.base.HotelBaseParam;

public class CheckPayStatusConvert extends HotelBaseParam {
	
	private String orderCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}
