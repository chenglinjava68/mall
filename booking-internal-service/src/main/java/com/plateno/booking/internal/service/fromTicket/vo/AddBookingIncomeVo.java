package com.plateno.booking.internal.service.fromTicket.vo;

import com.plateno.booking.internal.bean.request.custom.AddBookingParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.AbsRunningAccountVo;

public class AddBookingIncomeVo extends AbsRunningAccountVo {

	private AddBookingParam addBookingParam;
	
	private String orderNo;
	
	private String partnerOrderId;
	
	private String waitPaymentTime;

	public AddBookingParam getAddBookingParam() {
		return addBookingParam;
	}

	public void setAddBookingParam(AddBookingParam addBookingParam) {
		this.addBookingParam = addBookingParam;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}
	
	public void setWaitPaymentTime(String waitPaymentTime) {
		this.waitPaymentTime = waitPaymentTime;
	}

	@Override
	public Integer getMemberId() {
		return addBookingParam.getMemberId();
	}

	@Override
	public String getOrderNo() {
		return orderNo;
	}

	@Override
	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	@Override
	public String getWaitPaymentTime() {
		return waitPaymentTime;
	}

	@Override
	public Integer getChannel() {
		return addBookingParam.getChannel();
	}
}