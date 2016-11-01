package com.plateno.booking.internal.dao.pojo;

import java.util.Date;

import com.plateno.booking.internal.dao.pojo.base.BillBaseParam;

public class BillRefundDetail extends BillBaseParam {

	private String refundReason; // 退款原因
	private Integer refundType = 1; // 退款方式 1：原路返回
	private Integer factorage;
	private Date refundTime;
	private Date upTime;
	private String refundFail; // 退款失败原因
	private String goodsId;
	private String partnerOrderId;
	private Integer ticketStatus;
	private Integer ticketType;
	private String userName;
	private Integer billLogStatus;

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

	public Integer getFactorage() {
		return factorage;
	}

	public void setFactorage(Integer factorage) {
		this.factorage = factorage;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getBillLogStatus() {
		return billLogStatus;
	}

	public void setBillLogStatus(Integer billLogStatus) {
		this.billLogStatus = billLogStatus;
	}
}
