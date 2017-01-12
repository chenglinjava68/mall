package com.plateno.booking.internal.bean.response.custom;

public class MAddBookResponse {

	private String orderNo; // 订单编号
	private String goodsId; // 商品ID
	
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
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

}
