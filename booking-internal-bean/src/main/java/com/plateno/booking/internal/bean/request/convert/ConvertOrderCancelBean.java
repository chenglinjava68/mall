package com.plateno.booking.internal.bean.request.convert;

import com.plateno.booking.internal.bean.request.lvmama.base.BaseParam;

public class ConvertOrderCancelBean extends BaseParam {

	private String PartnerOrderNo; // 铂涛-票务ID
	
	private String orderNo; // 驴妈妈的订单ID

	public String getPartnerOrderNo() {
		return PartnerOrderNo;
	}

	public void setPartnerOrderNo(String partnerOrderNo) {
		PartnerOrderNo = partnerOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("timestamp=");
		buf.append(getTimestamp()).append("&appKey=").append(getAppKey()).append("&messageFormat=").append(getMessageFormat())
				.append("&sign=").append(getSign()).append("&PartnerOrderNo=").append(this.PartnerOrderNo).append("&orderId=")
				.append(this.orderNo);

		return buf.toString();

	}
}
