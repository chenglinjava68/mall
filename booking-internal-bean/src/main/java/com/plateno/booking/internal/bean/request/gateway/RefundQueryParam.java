package com.plateno.booking.internal.bean.request.gateway;

/**
 * 网关退款查询bean
 * 
 * @author user
 *
 */
public class RefundQueryParam {
	private String merchantNo;		//支付网关分配的商户
	private String refundOrderNo;	//标识商户的订单
	private String signData;		//封签

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

}
