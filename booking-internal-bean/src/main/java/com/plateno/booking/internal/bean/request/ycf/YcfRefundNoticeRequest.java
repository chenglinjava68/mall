package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 要出发退款入参
 * @author yi.wang
 * @date 2016年7月4日下午4:36:33
 * @version 1.0
 * @Description
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YcfRefundNoticeRequest implements Serializable {

	private static final long serialVersionUID = -1978390431239044504L;

	//【合】订单号
	private String partnerOrderId;		//铂涛订单号 ==>  orderNo

	//退款状态
	private Integer refundStatus;

	//退款金额
	private BigDecimal refundPrice;

	//退款手续费
	private BigDecimal refundCharge;

	//退款申请时间,客人发起退款的时间
	private String refundTime;

	//退款处理时间,要出发处理完退款的时间
	private String responseTime;

	//退款原因
	private String refundReason;
	
	//【要】处理退款的备注
	private String handleRemark;


	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public BigDecimal getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(BigDecimal refundPrice) {
		this.refundPrice = refundPrice;
	}

	public BigDecimal getRefundCharge() {
		return refundCharge;
	}

	public void setRefundCharge(BigDecimal refundCharge) {
		this.refundCharge = refundCharge;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

}
