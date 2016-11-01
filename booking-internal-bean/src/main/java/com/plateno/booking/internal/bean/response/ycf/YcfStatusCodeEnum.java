package com.plateno.booking.internal.bean.response.ycf;

/**
 * 要出发交互码
 * @author yi.wang
 * @date 2016年7月6日上午10:35:11
 * @version 1.0
 * @Description
 */
public enum YcfStatusCodeEnum {

	SUCCESS(200), //成功
	REQUEST_ERROR(400), //请求错误
	ERROR(500); //服务错误

	private int status;

	private YcfStatusCodeEnum(int state) {
		this.status = state;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
