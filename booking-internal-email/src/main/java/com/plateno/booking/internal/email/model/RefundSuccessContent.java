package com.plateno.booking.internal.email.model;

/**
 * 退款成功
 * @author mogt
 * @date 2016年11月15日
 */
public class RefundSuccessContent extends BaseContent {
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 金额
	 */
	private String money;
	
	/**
	 * 积分
	 */
	private String jf;

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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getJf() {
		return jf;
	}

	public void setJf(String jf) {
		this.jf = jf;
	}
	
}
