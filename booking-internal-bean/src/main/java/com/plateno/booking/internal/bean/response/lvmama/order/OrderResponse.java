package com.plateno.booking.internal.bean.response.lvmama.order;

import com.plateno.booking.internal.bean.response.custom.Order;
import com.plateno.booking.internal.bean.response.lvmama.base.ResponseState.State;

public class OrderResponse {

	private State state;
	
	private Order order;
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
}
