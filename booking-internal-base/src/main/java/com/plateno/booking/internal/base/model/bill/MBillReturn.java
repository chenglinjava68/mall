package com.plateno.booking.internal.base.model.bill;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class MBillReturn{
	private String tradeNo;
	private String orderNo;
	private String partnerOrderId;
	private Integer memberId;
	private String username;
	private String goodId;
	@JsonIgnore
	private Date visit_date;
	
	private long visitDate;
	
	private String mobile;
	private Integer paid; //已付金额(单位：分)
	private Integer channel;
	private Integer amount;
	private int payId;	 //支付方式(1-支付宝,2-微信,3-现金,4-银行卡,5-挂账)
	private int status; // 状态(100-初始;200-网关支付中;201-网关支付成功;202-网关支付失败;300-OTA支付中;301-OTA支付成功;302-OTA支付失败;400-OTA退款申请中;401-OTA退款成功;402-OTA退款失败;500-OTA网关退款申请中;501-网关退款成功;502-网关退款失败;)
	@JsonIgnore
	private Date create_time;
	
	private long createTime;
	private int ticketStatus;
	@JsonIgnore
	private Date payment_time;
	private long paymentTime;
	private Integer platform_id;
	private Integer quantity;
	
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public long getVisitDate() {
		return visit_date == null ? 0 : visit_date.getTime();
	}
	
	public long getPaymentTime() {
		return payment_time == null ? 0 : payment_time.getTime();
	}

	public Date getPayment_time() {
		return payment_time;
	}

	public void setPayment_time(Date payment_time) {
		this.payment_time = payment_time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getPaid() {
		return paid;
	}

	public void setPaid(Integer paid) {
		this.paid = paid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public int getPayId() {
		return payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	/*public long getVisitDate() {
		return visit_date.getTime();
	}*/
	
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getCreateTime() {
		return create_time.getTime();
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Date getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(Date visit_date) {
		this.visit_date = visit_date;
	}

	public int getTicketStatus() {
		return ticketStatus;
	}
	
	public void setTicketStatus(int ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Integer getPlatform_id() {
		return platform_id;
	}

	public void setPlatform_id(Integer platform_id) {
		this.platform_id = platform_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}