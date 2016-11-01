package com.plateno.booking.internal.bean.vo.hotel.response.inner;

public class OrderInvoice {
	private String invoiceTitle; // 发票抬头
	private String invoiceValue; // 发票内容
	private String invoiceType; // 领取方式
	private String invoiceCode; // 发票类型
	private String invoiceMsg; // 发票信息

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceValue() {
		return invoiceValue;
	}

	public void setInvoiceValue(String invoiceValue) {
		this.invoiceValue = invoiceValue;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceMsg() {
		return invoiceMsg;
	}

	public void setInvoiceMsg(String invoiceMsg) {
		this.invoiceMsg = invoiceMsg;
	}
}
