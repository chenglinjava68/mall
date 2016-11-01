package com.plateno.booking.internal.bean.vo.hotel.request;

/**
 * 支付酒店订单bean
 * 
 * @author user
 *
 */
public class PayHotelOrderRequest {
	private String orderCode;		//订单编码
	private Integer itemId;			//项目编码1：支付,2:退款,3:支付不限制金额,4:退款不限制金额,11:待扣款通知,12:扣款成功通知,13:扣款失败通知,14:待扣款通知不限制金额  (注意：支付服务调用只用1,2,11,12,13)

	private Double amount;			//支付（退款）金额
	private String transId;			//交易流水号
	private String merchantNo;		//商户号
	private Integer payChannel;		//支付渠道  区分第三方支付，1:3微信,5、7、8支付宝，9微信闪电住，11阿里信用住，13 翼支付
	private String ext;
	private String ext2;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
}
