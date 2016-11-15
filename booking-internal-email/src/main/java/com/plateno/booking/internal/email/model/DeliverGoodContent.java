package com.plateno.booking.internal.email.model;

/**
 * 发货提醒
 * @author mogt
 * @date 2016年11月15日
 */
public class DeliverGoodContent extends BaseContent {
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 快递名称
	 */
	private String express;
	
	/**
	 * 快递单号
	 */
	private String expressCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	
}
