package com.plateno.booking.internal.bean.response.custom;

import com.plateno.booking.internal.bean.response.custom.base.CustomBaseBill;

public class RefundBillResponse extends CustomBaseBill {
	private String refundReason; // 退款原因
	private Integer refundType = 1; // 退款方式 1：原路返回
	private long refundTime; // 发起退款时间
	private Integer factorage;
	private long upTime; // 退款成功/失败 时间
	private String refundFail; // 退款失败原因
	private String goodsId; // 商品ID
	private String partnerOrderId;
	private Integer ticketStatus;
	private Integer ticketType;
	private long visitDate;
	private String userName;

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public long getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(long refundTime) {
		this.refundTime = refundTime;
	}

	public Integer getFactorage() {
		return factorage;
	}

	public void setFactorage(Integer factorage) {
		this.factorage = factorage;
	}

	public long getUpTime() {
		return upTime;
	}

	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}

	public String getRefundFail() {
		return refundFail;
	}

	public void setRefundFail(String refundFail) {
		this.refundFail = refundFail;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}

	public long getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(long visitDate) {
		this.visitDate = visitDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
