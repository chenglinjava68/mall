package com.plateno.booking.internal.bean.response.gateway.refund;

import com.plateno.booking.internal.bean.response.gateway.base.BaseParam;

/**
 * 网关退款操作响应bean
 * 
 * @author user
 *
 */
public class RefundOrderResponse extends BaseParam {

	private String ext1;
	private String ext2;
	private String ext3;

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

}
