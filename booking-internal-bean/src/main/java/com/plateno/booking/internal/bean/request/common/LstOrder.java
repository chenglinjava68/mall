package com.plateno.booking.internal.bean.request.common;

import java.util.ArrayList;
import java.util.List;

public class LstOrder<T> extends PageReq<T> {
	private static final long serialVersionUID = 1L;

	List<T> orderList = new ArrayList<T>();

	public List<T> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<T> orderList) {
		this.orderList = orderList;
	}
}
