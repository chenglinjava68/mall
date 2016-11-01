package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 要出发支付入参
 * @author yi.wang
 * @date 2016年7月4日下午4:36:33
 * @version 1.0
 * @Description
 */
public class YcfPayOrderRequest implements Serializable {

	private static final long serialVersionUID = 1970563730424434936L;

	//【合】订单号
	private String partnerOrderId;

	//支付金额
	private BigDecimal price;

	//支付流水号
	private String paySerialNumber;

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPaySerialNumber() {
		return paySerialNumber;
	}

	public void setPaySerialNumber(String paySerialNumber) {
		this.paySerialNumber = paySerialNumber;
	}

}
