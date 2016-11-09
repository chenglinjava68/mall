package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;

import com.plateno.booking.internal.base.model.BaseParam;

public class MOperateLogParam extends BaseParam implements Serializable{
	
	private static final long serialVersionUID = -2133365201138730288L;

	private Integer operateType;
	
	private String operateUserid;
	
	private String operateUsername;
	
	private String remark;
	
	private String orderCode;

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	
	

	public String getOperateUserid() {
		return operateUserid;
	}

	public void setOperateUserid(String operateUserid) {
		this.operateUserid = operateUserid;
	}

	public String getOperateUserName() {
		return operateUsername;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUsername = operateUserName;
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
