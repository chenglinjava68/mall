package com.plateno.booking.internal.bean.request.custom;

import javax.validation.constraints.NotNull;

public class ReceiptParam {
	
	@NotNull(message = "订单编码,不能为空")
	private String orderNo; // 订单编码
	
	@NotNull(message = "收货人姓名,不能为空")
	private String receiptName;//收货人姓名
	
	@NotNull(message = "收货人手机,不能为空")
	private String receiptMobile;//收货人手机
	
	@NotNull(message = "收货地址,不能为空")
	private String receiptAddress;//收货地址
	
	private String operateUserid;
	
	private String operateUsername;
	
	private Integer plateForm; //1供应商后台、2营销通后台

	public String getOperateUserid() {
		return operateUserid;
	}

	public void setOperateUserid(String operateUserid) {
		this.operateUserid = operateUserid;
	}

	public String getOperateUsername() {
		return operateUsername;
	}

	public void setOperateUsername(String operateUsername) {
		this.operateUsername = operateUsername;
	}

	public Integer getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(Integer plateForm) {
		this.plateForm = plateForm;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReceiptName() {
		return receiptName;
	}

	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}

	public String getReceiptMobile() {
		return receiptMobile;
	}

	public void setReceiptMobile(String receiptMobile) {
		this.receiptMobile = receiptMobile;
	}

	public String getReceiptAddress() {
		return receiptAddress;
	}

	public void setReceiptAddress(String receiptAddress) {
		this.receiptAddress = receiptAddress;
	}
	
	
}
