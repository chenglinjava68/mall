package com.plateno.booking.internal.service.fromTicket.vo;

import com.plateno.booking.internal.bean.request.custom.CannelBookingParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.AbsRunningAccountVo;

public class CancelBookingIncomeVo extends AbsRunningAccountVo {

	private CannelBookingParam cancelBookingParam;

	
	public CannelBookingParam getCancelBookingParam() {
		return cancelBookingParam;
	}


	public void setCancelBookingParam(CannelBookingParam cancelBookingParam) {
		this.cancelBookingParam = cancelBookingParam;
	}

	@Override
	public Integer getMemberId() {
		return cancelBookingParam.getMemberId();
	}


	@Override
	public Integer getChannel() {
		return cancelBookingParam.getChannel();
	}


	@Override
	public String getTradeNo() {
		return cancelBookingParam.getTradeNo();
	}
	
	
}