package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;

/**
 * 要出发申请取消订单入参
 * @author yi.wang
 * @date 2016年7月4日下午4:36:33
 * @version 1.0
 * @Description
 */
public class YcfCancelOrderRequest implements Serializable {

	private static final long serialVersionUID = 1027981243810683478L;

	//【合】订单号
	private String partnerOrderId;

	//取消原因备注
	private String remark;

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
