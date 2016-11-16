package com.plateno.booking.internal.bean.request.custom;

import javax.validation.constraints.NotNull;

import com.plateno.booking.internal.base.model.BaseParam;

public class ReceiptParam extends BaseParam {
	
	private static final long serialVersionUID = 1L;

	@NotNull(message = "订单编码,不能为空")
	private String orderNo; // 订单编码
	
	@NotNull(message = "收货人姓名,不能为空")
	private String receiptName;//收货人姓名
	
	@NotNull(message = "收货人手机,不能为空")
	private String receiptMobile;//收货人手机
	
	@NotNull(message = "收货地址,不能为空")
	private String receiptAddress;//收货地址
	
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
