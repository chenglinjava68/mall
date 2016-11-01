package com.plateno.booking.internal.bean.response.ycf;

import java.io.Serializable;

import java.util.List;

import com.plateno.booking.internal.bean.request.ycf.Vocher;


/**
 * 要出发查询订单返回参数
 * @author yi.wang
 * @date 2016年7月6日上午11:47:02
 * @version 1.0
 * @Description
 */
public class YcfOrderStatusResponse implements Serializable {

	private static final long serialVersionUID = -609618108139376522L;

	private Integer orderStatus;

	private List<Vocher> vochers;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Vocher> getVochers() {
		return vochers;
	}

	public void setVochers(List<Vocher> vochers) {
		this.vochers = vochers;
	}
}
