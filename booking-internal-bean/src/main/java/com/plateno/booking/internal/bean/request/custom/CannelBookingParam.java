package com.plateno.booking.internal.bean.request.custom;

import com.plateno.booking.internal.bean.request.custom.base.BaseParam;

/**
 * @author user
 *
 */
public class CannelBookingParam extends BaseParam {
	private String reason;

	private String goodId; // 商品ID

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
}
