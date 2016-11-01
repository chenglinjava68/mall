package com.plateno.booking.internal.bean.response.lvmama.order;

import java.util.List;

import com.plateno.booking.internal.bean.response.lvmama.base.ResponseState;
import com.plateno.booking.internal.bean.response.lvmama.order.LvMaMaOrderBean.SuccessOrder;

public class SuccessPayBean {
	private ResponseState state;
	
	private List<SuccessOrder> orderList;
	
	public ResponseState getState() {
		return state;
	}

	public void setState(ResponseState state) {
		this.state = state;
	}

	public List<SuccessOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<SuccessOrder> orderList) {
		this.orderList = orderList;
	}

}