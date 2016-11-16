package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;

import com.plateno.booking.internal.base.model.BaseParam;

public class MOperateLogParam extends BaseParam implements Serializable{
	
	private static final long serialVersionUID = -2133365201138730288L;

	private Integer operateType;
	
	private String remark;
	
	private String orderCode;

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	
	
	
	
}
