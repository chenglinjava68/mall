package com.plateno.booking.internal.bean.request.custom;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ActivityOrderParam {
	@NotNull(message = "铂涛-票务tradeNo(账单ID),不能为空")
	@NotEmpty(message = "铂涛-票务tradeNo(账单ID),不能为空")
	private String tradeNo; // 账单编码
	
	
	@NotNull(message = "会员ID不能为空")
	private Integer memberId; // 会员 id


	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
}
