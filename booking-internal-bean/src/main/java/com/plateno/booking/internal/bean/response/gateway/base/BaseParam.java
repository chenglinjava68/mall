package com.plateno.booking.internal.bean.response.gateway.base;

public class BaseParam {
	private String code;			//结果码
	private String message;			//结果消息
	private String referenceId;		//网关发送支付渠道订单

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}

