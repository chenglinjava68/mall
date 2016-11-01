package com.plateno.booking.internal.bean.response.custom;

import java.util.List;

import com.plateno.booking.internal.bean.response.custom.base.CustomBaseBill;
import com.plateno.booking.internal.bean.response.custom.base.TravellersInfo;

public class CustomBillDetail extends CustomBaseBill {
	
	private String goodsId; // 商品ID
	private String partnerOrderId;
	private Integer rate; // 佣金率
	private Integer ticketStatus;
	private Integer ticketType;
	private Integer quantity;
	private Integer commission;
	private List<TravellersInfo> travellers;
	private Long visitDate;
	private Long waitPaymentTime;
	
	private String username;
	
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
	public List<TravellersInfo> getTravellers() {
		return travellers;
	}
	public void setTravellers(List<TravellersInfo> travellers) {
		this.travellers = travellers;
	}
	public Long getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Long visitDate) {
		this.visitDate = visitDate;
	}
	public Long getWaitPaymentTime() {
		return waitPaymentTime;
	}
	public void setWaitPaymentTime(Long waitPaymentTime) {
		this.waitPaymentTime = waitPaymentTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	

}
