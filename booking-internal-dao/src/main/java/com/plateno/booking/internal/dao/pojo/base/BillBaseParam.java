package com.plateno.booking.internal.dao.pojo.base;

import java.util.Date;

/**
 * @author user
 * 
 * bill表,账单信息基础Bean
 *
 */
public class BillBaseParam {

	private Long billId; 		// billId
	private String tradeNo; 	// 账单ID
	private String orderNo;	 	// 订单ID
	private Integer payId; 		// 支付方式(1-支付宝,2-微信,3-现金,4-银行卡,5-挂账)
	private Integer paid; 		//已付金额(单位：分)
	private Integer memberId; 	// 会员ID
	private String mobile; 		// 手机号码
	private Integer channel;	// 渠道
	private Integer amount; 	// 订单金额
	private Integer status; 		// 状态(100-初始;200-网关支付中;201-网关支付成功;202-网关支付失败;300-OTA支付中;301-OTA支付成功;302-OTA支付失败;400-OTA退款申请中;401-OTA退款成功;402-OTA退款失败;500-OTA网关退款申请中;501-网关退款成功;502-网关退款失败;)
	private Integer platformId;	// 平台
	private String openId; 		// 微信OpenId
	private Date createTime;	//创建时间
	private Integer isDelete;	//是否被删除
	private Date visitDate;		//游玩日期
	
	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getPaid() {
		return paid;
	}

	public void setPaid(Integer paid) {
		this.paid = paid;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
}
