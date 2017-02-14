package com.plateno.booking.internal.service.fromTicket.vo;

import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.AbsRunningAccountVo;

public class MAddBookingIncomeVo extends AbsRunningAccountVo {

	private MAddBookingParam addBookingParam;
	
	private String orderNo;
	
	private String partnerOrderId;
	
	private String waitPaymentTime;

	
	
	@Override
    public String toString() {
        return "MAddBookingIncomeVo [addBookingParam=" + addBookingParam + ", orderNo=" + orderNo
                + ", partnerOrderId=" + partnerOrderId + ", waitPaymentTime=" + waitPaymentTime
                + "]";
    }

    public MAddBookingParam getAddBookingParam() {
		return addBookingParam;
	}

	public void setAddBookingParam(MAddBookingParam addBookingParam) {
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
		return addBookingParam.getChanelId();
	}
}