package com.plateno.booking.internal.bean.response.custom;

/**
 * 驴妈妈 要出发 同程 构建返回给中间层
 * @author lingjw
 * @version 创建时间：2016年8月23日 下午3:51:15 
 * 说明:
 */
public class Order {

	private String orderId;
	private String status;
	private String paymentStatus;
	private String waitPaymentTime;

	
	public Order(String orderId, String status, String paymentStatus, String waitPaymentTime) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.paymentStatus = paymentStatus;
		this.waitPaymentTime = waitPaymentTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
