package com.plateno.booking.internal.dao.pojo;

public class BillLogDetail extends BillDetails {
	private String billLogTradeNo;
	private String referenceId;
	private Integer factorage; // 单位：分
	private Integer type; // 支付类型： 1 ： 收入 2：支出
	private Integer billLogStatus;	//是否支付完成

	public String getBillLogTradeNo() {
		return billLogTradeNo;
	}

	public void setBillLogTradeNo(String billLogTradeNo) {
		this.billLogTradeNo = billLogTradeNo;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Integer getFactorage() {
		return factorage;
	}

	public void setFactorage(Integer factorage) {
		this.factorage = factorage;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getBillLogStatus() {
		return billLogStatus;
	}

	public void setBillLogStatus(Integer billLogStatus) {
		this.billLogStatus = billLogStatus;
	}
}
