package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * 要出发推送订单状态入参
 * @author yi.wang
 * @date 2016年7月6日上午10:27:26
 * @version 1.0
 * @Description
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YcfOrderStatusRequest implements Serializable {

	private static final long serialVersionUID = 8086908391435116801L;

	//【合】订单号
	private String partnerOrderId;

	//订单状态
	private Integer orderStatus;

	//电子凭证码
	private List<Vocher> vochers;

	//取消订单的原因等描述信息
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

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
