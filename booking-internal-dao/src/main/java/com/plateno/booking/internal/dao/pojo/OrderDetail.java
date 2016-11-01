package com.plateno.booking.internal.dao.pojo;

import com.plateno.booking.internal.dao.pojo.base.BillBaseParam;

public class OrderDetail extends BillBaseParam {
	
	private Integer orderId;
	private Integer ticketStatus; // 电子凭证使用状态(1-实体商品,2-电子票未使用,3-已使用)
	private Integer goodsId;
	private String  partnerOrderId;
	
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}
}
