package com.plateno.booking.internal.bean.request.tc.order;

public class OrderListByPageBody {
	private String orderSerialId;
	private Integer pageIndex = 1;
	private Integer pageSize = 20;

	public String getOrderSerialId() {
		return orderSerialId;
	}

	public void setOrderSerialId(String orderSerialId) {
		this.orderSerialId = orderSerialId;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
