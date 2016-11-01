package com.plateno.booking.internal.bean.request.custom;
import javax.validation.constraints.NotNull;

public class OTAPayBean {

	@NotNull(message = "账单ID,不能为空")
	private String tradeNo; // 账单ID
	@NotNull(message = "订单ID,不能为空")
	private String orderNo; // 订单ID
	//@NotNull(message = "订单支付流水,不能为空")
	private String billLogTradeNo; // 订单支付流水
	@NotNull(message = "会员ID,不能为空")
	private String memberId; // 会员ID
	//@NotNull(message = "网关参考号,不能为空")
	private String referenceId; // 支付网关返回的参考号

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

	public String getBillLogTradeNo() {
		return billLogTradeNo;
	}

	public void setBillLogTradeNo(String billLogTradeNo) {
		this.billLogTradeNo = billLogTradeNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}