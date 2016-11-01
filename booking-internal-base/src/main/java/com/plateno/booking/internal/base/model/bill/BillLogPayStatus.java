package com.plateno.booking.internal.base.model.bill;

public class BillLogPayStatus {
	private Integer billId;
	private String tradeNo;
	private String orderNo;
	private Integer type;
	private Integer status;
	private String goodId;
	private Integer factorage;
	private String billLogTradeNo;
	
	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public Integer getFactorage() {
		return factorage;
	}

	public void setFactorage(Integer factorage) {
		this.factorage = factorage;
	}

	public String getBillLogTradeNo() {
		return billLogTradeNo;
	}

	public void setBillLogTradeNo(String billLogTradeNo) {
		this.billLogTradeNo = billLogTradeNo;
	}
}
