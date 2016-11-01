package com.plateno.booking.internal.bean.request.convert;

public class ConvertOrderParam {
	
	private String orderId;				//第三方订单ID
	private Integer status;				//订单状态
	private String approveStatus;		//订单审核状态
	private String paymentStatus;		//支付状态
	private String waitPaymentTime;		//支付等待时间
	private String credenctStatus;		//凭证发送状态
	private Integer performStatus;		//凭证使用状态
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getWaitPaymentTime() {
		return waitPaymentTime;
	}

	public void setWaitPaymentTime(String waitPaymentTime) {
		this.waitPaymentTime = waitPaymentTime;
	}

	public String getCredenctStatus() {
		return credenctStatus;
	}

	public void setCredenctStatus(String credenctStatus) {
		this.credenctStatus = credenctStatus;
	}

	public Integer getPerformStatus() {
		return performStatus;
	}

	public void setPerformStatus(Integer performStatus) {
		this.performStatus = performStatus;
	}
}
