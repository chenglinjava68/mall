package com.plateno.booking.internal.bean.request.custom;

import javax.validation.constraints.NotNull;

import com.plateno.booking.internal.base.model.BaseParam;

public class ModifyOrderParams extends BaseParam {
	
	private static final long serialVersionUID = 1L;

	@NotNull(message="订单编码,不能为空")
	private String orderNo;
	
	@NotNull(message="状态不能为空")
	private Integer newStatus;
	
	private String remark;
	
	private Integer payAmount;
	
	private Integer point;
	
	private Integer refundAmount;
	
	private String operateUserid;
	
	private String operateUsername;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Integer newStatus) {
		this.newStatus = newStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
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
	

}
