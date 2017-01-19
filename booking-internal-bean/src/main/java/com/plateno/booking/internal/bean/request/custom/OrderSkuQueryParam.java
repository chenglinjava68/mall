package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.plateno.booking.internal.base.model.BaseParam;



public class OrderSkuQueryParam extends BaseParam implements Serializable {
	
	private static final long serialVersionUID = 1205169223416028536L;
	
	/**
	 * 库存ID
	 */
	@NotNull(message = "库存ID不能为空")
	@NotEmpty(message = "库存ID不能为空")
	private Integer skuId;

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
}