package com.plateno.booking.internal.bean.response.ycf;

import java.io.Serializable;

public class Response<T> implements Serializable {

	private static final long serialVersionUID = 4317710224434359115L;

	private int total;

	private T data;

	private boolean success;

	private String message;

	//200成功 400请求错误 500服务错误
	private int statusCode;

	private String partnerId;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
