package com.plateno.booking.internal.bean.response.custom;

import com.plateno.booking.internal.bean.response.custom.base.RefundActivityBean.RefundBill;

public class RefundResultReturn extends RefundBill {

	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}