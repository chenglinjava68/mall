package com.plateno.booking.internal.bean.request.tc.order;

import java.util.List;

public class ResendCodeBody {
	private List<String> serialId;
	
	private String sendMobile;

	public List<String> getSerialId() {
		return serialId;
	}

	public void setSerialId(List<String> serialId) {
		this.serialId = serialId;
	}

	public String getSendMobile() {
		return sendMobile;
	}

	public void setSendMobile(String sendMobile) {
		this.sendMobile = sendMobile;
	}
}
