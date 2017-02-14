package com.plateno.booking.internal.bean.response.custom;

public class MAddBookResponse {

	private String orderNo; // 订单编号
	
	/**
	 * 前端显示状态
	 */
	private Integer viewStatus;
	
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	
	
	public Integer getViewStatus() {
		return viewStatus;
	}
	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


}
