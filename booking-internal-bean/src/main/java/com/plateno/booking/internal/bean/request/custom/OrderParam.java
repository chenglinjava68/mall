package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;

import com.plateno.booking.internal.bean.request.custom.base.BaseParam;


public class OrderParam extends BaseParam implements Serializable {
	
	private static final long serialVersionUID = 1205169223416028536L;
	
	private String storeId;	     //分店ID

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}


	
}