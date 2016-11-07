package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;



public class MOrderParam implements Serializable {
	
	private static final long serialVersionUID = 1205169223416028536L;
	
	private Integer memberId; // 会员 id
	
	@NotNull(message = "订单编号不能为空")
	@NotEmpty(message = "订单编号不能为空")
	private String orderNo; // 订单编码
	
	private String logisticsNo; // 物流编号
	
	private Integer logisticsType; // 物流类型(1 圆通、2申通、3韵达、4百事通、5顺丰、6 EMS)
	
	
	private String refundRemark; //退款原因 

	
	private String operateUserid;
	
	private String operateUsername;
	
	private Integer plateForm; //1供应商后台、2营销通后台
	
	

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}

	public Integer getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(Integer logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getOperateUserid() {
		return operateUserid;
	}

	public void setOperateUserid(String operateUserid) {
		this.operateUserid = operateUserid;
	}

	public String getOperateUsername() {
		return operateUsername;
	}

	public void setOperateUsername(String operateUsername) {
		this.operateUsername = operateUsername;
	}

	public Integer getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(Integer plateForm) {
		this.plateForm = plateForm;
	}

	
	
	
}