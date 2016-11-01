package com.plateno.booking.internal.dao.pojo.bill;

import java.util.Date;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;

public class OrderDetailsReturn {
	private Integer id;
	private String goodId;		//商品ID
	private Integer amount;		//订单总金额
	private String orderNo;		//订单ID
	private String tradeNo;		//账单ID
	private String partnerOrderId;
	private String billState;	//订单状态
	private Integer rate;	//佣金率
	private Integer ticketStatus;
	private Integer ticketType;
	private Integer quantity;
	private List<TravellersInfo> travellersInfos;
	private long createTime;
	private Integer commission;
	private String mobile;
	private Integer platform_id;
	@JsonIgnore
	private Date create_time;
	
	private Integer payId;
	@JsonIgnore 
	private String travellers;
	
	//private long reason;		
	private long visitDate;		//游玩日期
	
	@JsonIgnore 
	private Date visit_date;
	
	@JsonIgnore
	private Date times;
	
	private long time;			//场次
	
	@JsonIgnore
	private Date wait_payment_time;
	
	private long waitPaymentTime;
	
	private Integer factorage;		//退款手续费
	
	private Integer channel;
	
	private String openId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<TravellersInfo> getTravellersInfos() {
		return travellersInfos;
	}

	public void setTravellersInfos(List<TravellersInfo> travellersInfos) {
		this.travellersInfos = travellersInfos;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getOrderNo() {
		return orderNo;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}
	
	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public long getVisitDate() {
		return visit_date.getTime();
	}

	public void setVisitDate(long visitDate) {
		this.visitDate = visitDate;
	}

	public long getTime() {
		return times == null ? 0 : times.getTime();
	}

	public String getTravellers() {
		return travellers;
	}

	public void setTravellers(String travellers) {
		this.travellers = travellers;
	}

	public Date getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(Date visit_date) {
		this.visit_date = visit_date;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public long getCreateTime() {
		return create_time.getTime();
	}

	public Date getTimes() {
		return times;
	}

	public void setTimes(Date times) {
		this.times = times;
	}

	public Date getWait_payment_time() {
		return wait_payment_time;
	}

	public void setWait_payment_time(Date wait_payment_time) {
		this.wait_payment_time = wait_payment_time;
	}

	public long getWaitPaymentTime() {
		return wait_payment_time == null ? 0 : wait_payment_time.getTime();
	}

	public Integer getFactorage() {
		return factorage;
	}

	public void setFactorage(Integer factorage) {
		this.factorage = factorage;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getPlatform_id() {
		return platform_id;
	}

	public void setPlatform_id(Integer platform_id) {
		this.platform_id = platform_id;
	}
}
