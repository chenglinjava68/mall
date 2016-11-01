package com.plateno.booking.internal.base.model.bill;

import java.util.List;

import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderProduct;

public class OrderProductInfo {
	
	private Order order;
	
	private List<OrderProduct> OrderProductList;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderProduct> getOrderProductList() {
		return OrderProductList;
	}

	public void setOrderProductList(List<OrderProduct> orderProductList) {
		OrderProductList = orderProductList;
	}

	
}
