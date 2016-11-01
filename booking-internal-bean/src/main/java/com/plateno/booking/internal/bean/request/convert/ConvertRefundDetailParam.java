package com.plateno.booking.internal.bean.request.convert;

/**
 * 退款bean转换对象
 * 
 * @author user
 *
 */
public class ConvertRefundDetailParam {

	private Integer code; // 状态码

	private String refundReason; // 退款原因

	private Integer refundStatus; // 退款状态

	private Integer refundPrice; // 退款金额

	private Integer refundCharge; // 退款手续费

	private String partnerOrderId; // 【合作方】第三方订单号

	private String tradeNo; // 铂涛账单号(支付流水号/账单ID)L/B
	
	private String orderNo; // 铂涛订单号(订单ID)O

	private String remark; // 备注

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Integer refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Integer getRefundCharge() {
		return refundCharge;
	}

	public void setRefundCharge(Integer refundCharge) {
		this.refundCharge = refundCharge;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
