package com.plateno.booking.internal.dao.pojo;

import java.util.Date;

import com.plateno.booking.internal.dao.pojo.base.BillBaseParam;

public class BillDetails extends BillBaseParam {

	private String goodsId; // 商品ID
	private String partnerOrderId;
	private Integer rate; // 佣金率
	private Integer ticketStatus;
	private Integer ticketType;
	private Integer quantity;
	private Integer commission;
	private String travellers;
	private Date visitDate;
	private Date waitPaymentTime;
	private String username;
	private String billLogtradeNo;
	private Integer shareId;
	private Integer viewStatus;
	private Date upTime;
	private Integer isActivity;

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

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public String getTravellers() {
		return travellers;
	}

	public void setTravellers(String travellers) {
		this.travellers = travellers;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getWaitPaymentTime() {
		return waitPaymentTime;
	}

	public void setWaitPaymentTime(Date waitPaymentTime) {
		this.waitPaymentTime = waitPaymentTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBillLogtradeNo() {
		return billLogtradeNo;
	}

	public void setBillLogtradeNo(String billLogtradeNo) {
		this.billLogtradeNo = billLogtradeNo;
	}

	public Integer getShareId() {
		return shareId;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}

	public Integer getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public Integer getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(Integer isActivity) {
		this.isActivity = isActivity;
	}
}
