package com.plateno.booking.internal.bean.response.custom;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class MOperateLogResponse  implements Serializable{
	
	private static final long serialVersionUID = -2133365201138730288L;

	private Integer id;
	
	private Integer operateType;
	
	private String operateUserid;
	
	private String operateUserName;
	
	private String remark;
	
	private Integer plateForm;
	
	private String orderCode;
	
	private Long operateTime;

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
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(Integer plateForm) {
		this.plateForm = plateForm;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}

	
	
	
	
}
