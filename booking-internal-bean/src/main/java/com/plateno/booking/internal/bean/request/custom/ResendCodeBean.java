package com.plateno.booking.internal.bean.request.custom;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * ClassName: OrderCancalBean
 * 
 * @Description: 类初始化的时候,存在继承关系,预先初始化子类
 * @author liulianyuan
 * @date 2016年3月25日
 */
public class ResendCodeBean {

	@NotNull(message = "铂涛-票务tradeNo(账单ID),不能为空")
	@NotEmpty(message = "铂涛-票务tradeNo(账单ID),不能为空")
	private String tradeNo;

	private Integer channel;

	private String mobile;

	public ResendCodeBean(String tradeNo, String orderId)throws Exception {
		this.tradeNo = tradeNo;
	}

	public ResendCodeBean() throws Exception {}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
