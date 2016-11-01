package com.plateno.booking.internal.service.fromTicket.vo;

import com.plateno.booking.internal.bean.request.convert.ConvertRefundDetailParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.AbsRunningAccountVo;

public class RefundAccountIncomeVo extends AbsRunningAccountVo {

	private ConvertRefundDetailParam refundDetailParam;
	
	private Long billId;
	private String tradeNo;			//账单ID
	private Integer memberId;		//会员ID
	private String billLogTradeNo;	//支付流水
	private Integer channel;
	private Integer status;
	private Integer money;
	
	public ConvertRefundDetailParam getRefundDetailParam() {
		return refundDetailParam;
	}

	public void setRefundDetailParam(ConvertRefundDetailParam refundDetailParam) {
		this.refundDetailParam = refundDetailParam;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

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

	public String getBillLogTradeNo() {
		return billLogTradeNo;
	}

	public void setBillLogTradeNo(String billLogTradeNo) {
		this.billLogTradeNo = billLogTradeNo;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
}
