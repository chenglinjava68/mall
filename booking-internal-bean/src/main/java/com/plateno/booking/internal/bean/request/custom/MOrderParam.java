package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.plateno.booking.internal.base.model.BaseParam;



public class MOrderParam extends BaseParam implements Serializable {
	
	private static final long serialVersionUID = 1205169223416028536L;
	
	@NotNull(message = "订单编号不能为空")
	@NotEmpty(message = "订单编号不能为空")
	private String orderNo; // 订单编码
	
	private String logisticsNo; // 物流编号
	
	private Integer logisticsType; // 物流类型(1 圆通、2申通、3韵达、4百事通、5顺丰、6 EMS)
	
	
	private String refundRemark; //退款原因 

	
	private String operateUserid;
	
	private String operateUsername;
	
	/**
	 * 取消类型：1-超时取消， 2-手动取消（兼容旧的接口，不填判别为手动取消）超时取消会判断时间是否已经超过30分钟
	 */
	private Integer type;
	
	/**
	 * 支付方式
	 * 1微信支付、2支付宝支付
	 */
	private Integer payType;
	
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
	
}