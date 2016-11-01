package com.plateno.booking.internal.bean.vo.hotel.response.base;

/**
 * 酒店返回基础结构
 * 
 * @author user
 *
 * @param <T>
 */
public class HotelResponse<T> {

	private String resultCode;
	private String resultMsg;
	private T data;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
