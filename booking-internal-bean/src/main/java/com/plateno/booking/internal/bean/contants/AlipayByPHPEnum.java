package com.plateno.booking.internal.bean.contants;
/**
 * PHP支付回调返回
 * 
 * @author user
 * 
 */
public enum AlipayByPHPEnum {

	SUCCESS(1, "成功"),
	FAILURE(2, "失败"),
	TICKETING(3, "出票中");

	private Integer index;
	private String name;

	private AlipayByPHPEnum(Integer index, String name) {
		this.index = index;
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}