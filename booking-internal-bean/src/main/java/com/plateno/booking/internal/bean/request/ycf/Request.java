package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;

public class Request<T> implements Serializable {

	private static final long serialVersionUID = -1995640445378017097L;
	
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
