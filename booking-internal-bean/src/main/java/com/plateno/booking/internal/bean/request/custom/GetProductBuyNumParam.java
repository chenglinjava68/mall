package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.plateno.booking.internal.base.model.BaseParam;


/**
 * 获取商品的购买数量
 * @author mogt
 * @date 2016年11月29日
 */
public class GetProductBuyNumParam extends BaseParam implements Serializable {
	
	private static final long serialVersionUID = 1205169223416028536L;
	
	
	/**
	 * 商品ID
	 */
	@NotNull(message = "请输入产品编号")
	private Integer productId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}