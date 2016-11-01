package com.plateno.booking.internal.bean.vo.hotel.response;

/**
 * 支付凭证返回
 * 
 * @author user
 * 
 */
public class SNResponse {
	private String sn;		//交易凭证,用于新增订单

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
