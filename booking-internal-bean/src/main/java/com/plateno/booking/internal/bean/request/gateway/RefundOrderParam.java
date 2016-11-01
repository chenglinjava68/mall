package com.plateno.booking.internal.bean.request.gateway;

/**
 * 网关退款操作bean
 * 
 * @author user
 *
 */
public class RefundOrderParam {
	private String merchantNo;		//支付网关分配的商户
	private String orderNo;			//原交易订单号
	private String refundOrderNo;	//退款订单号
	private Integer refundAmount;	//支付金额(单位：分)
	private String signData;		//封签
	private String remark;			//备注

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
