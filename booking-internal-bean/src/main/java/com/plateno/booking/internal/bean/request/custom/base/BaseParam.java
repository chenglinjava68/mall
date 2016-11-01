package com.plateno.booking.internal.bean.request.custom.base;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class BaseParam {

	@NotNull(message = "铂涛-票务tradeNo(账单ID),不能为空")
	@NotEmpty(message = "铂涛-票务tradeNo(账单ID),不能为空")
	private String tradeNo; // 账单编码
	
	@NotNull(message = "订单所属渠道不能为空")
	private Integer channel; // 渠道
	
	private Integer hotelChannel = 1;
	
	private Integer memberId; // 会员 id
	
	private String orderNo; // 订单编码

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getHotelChannel() {
		return hotelChannel;
	}

	public void setHotelChannel(Integer hotelChannel) {
		this.hotelChannel = hotelChannel;
	}
}
