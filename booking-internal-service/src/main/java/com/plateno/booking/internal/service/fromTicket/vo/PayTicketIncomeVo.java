package com.plateno.booking.internal.service.fromTicket.vo;

import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.interceptor.adam.common.bean.AbsRunningAccountVo;

public class PayTicketIncomeVo extends AbsRunningAccountVo {
	
	private BillDetails billDetails;

	private String goodsId; // 商品ID
	private String billLogtradeNo;
	private Long billId; 		// billId
	private String tradeNo; 	// 账单ID
	private String orderNo;	 	// 订单ID
	private Integer memberId; 	// 会员ID
	private Integer channel;	// 渠道
	private Integer amount; 	// 订单金额
	private Integer status; 		// 状态(100-初始;200-网关支付中;201-网关支付成功;202-网关支付失败;300-OTA支付中;301-OTA支付成功;302-OTA支付失败;400-OTA退款申请中;401-OTA退款成功;402-OTA退款失败;500-OTA网关退款申请中;501-网关退款成功;502-网关退款失败;)
	private String openId; 		// 微信OpenId
	
	private String referenceId;
	
	public BillDetails getBillDetails() {
		return billDetails;
	}
	public void setBillDetails(BillDetails billDetails) {
		this.billDetails = billDetails;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getBillLogtradeNo() {
		return billLogtradeNo;
	}
	public void setBillLogtradeNo(String billLogtradeNo) {
		this.billLogtradeNo = billLogtradeNo;
	}
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
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}
